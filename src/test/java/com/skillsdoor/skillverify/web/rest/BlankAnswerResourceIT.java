package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.BlankAnswerAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.BlankAnswer;
import com.skillsdoor.skillverify.domain.BlankSpace;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.repository.BlankAnswerRepository;
import com.skillsdoor.skillverify.service.dto.BlankAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.BlankAnswerMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BlankAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlankAnswerResourceIT {

    private static final String DEFAULT_ANSWER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final String ENTITY_API_URL = "/api/blank-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BlankAnswerRepository blankAnswerRepository;

    @Autowired
    private BlankAnswerMapper blankAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlankAnswerMockMvc;

    private BlankAnswer blankAnswer;

    private BlankAnswer insertedBlankAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlankAnswer createEntity(EntityManager em) {
        BlankAnswer blankAnswer = new BlankAnswer().answerText(DEFAULT_ANSWER_TEXT).isCorrect(DEFAULT_IS_CORRECT);
        // Add required entity
        CandidateAnswer candidateAnswer;
        if (TestUtil.findAll(em, CandidateAnswer.class).isEmpty()) {
            candidateAnswer = CandidateAnswerResourceIT.createEntity(em);
            em.persist(candidateAnswer);
            em.flush();
        } else {
            candidateAnswer = TestUtil.findAll(em, CandidateAnswer.class).get(0);
        }
        blankAnswer.setCandidateAnswer(candidateAnswer);
        // Add required entity
        BlankSpace blankSpace;
        if (TestUtil.findAll(em, BlankSpace.class).isEmpty()) {
            blankSpace = BlankSpaceResourceIT.createEntity(em);
            em.persist(blankSpace);
            em.flush();
        } else {
            blankSpace = TestUtil.findAll(em, BlankSpace.class).get(0);
        }
        blankAnswer.setBlankSpace(blankSpace);
        return blankAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlankAnswer createUpdatedEntity(EntityManager em) {
        BlankAnswer updatedBlankAnswer = new BlankAnswer().answerText(UPDATED_ANSWER_TEXT).isCorrect(UPDATED_IS_CORRECT);
        // Add required entity
        CandidateAnswer candidateAnswer;
        if (TestUtil.findAll(em, CandidateAnswer.class).isEmpty()) {
            candidateAnswer = CandidateAnswerResourceIT.createUpdatedEntity(em);
            em.persist(candidateAnswer);
            em.flush();
        } else {
            candidateAnswer = TestUtil.findAll(em, CandidateAnswer.class).get(0);
        }
        updatedBlankAnswer.setCandidateAnswer(candidateAnswer);
        // Add required entity
        BlankSpace blankSpace;
        if (TestUtil.findAll(em, BlankSpace.class).isEmpty()) {
            blankSpace = BlankSpaceResourceIT.createUpdatedEntity(em);
            em.persist(blankSpace);
            em.flush();
        } else {
            blankSpace = TestUtil.findAll(em, BlankSpace.class).get(0);
        }
        updatedBlankAnswer.setBlankSpace(blankSpace);
        return updatedBlankAnswer;
    }

    @BeforeEach
    void initTest() {
        blankAnswer = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedBlankAnswer != null) {
            blankAnswerRepository.delete(insertedBlankAnswer);
            insertedBlankAnswer = null;
        }
    }

    @Test
    @Transactional
    void createBlankAnswer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BlankAnswer
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);
        var returnedBlankAnswerDTO = om.readValue(
            restBlankAnswerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankAnswerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BlankAnswerDTO.class
        );

        // Validate the BlankAnswer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBlankAnswer = blankAnswerMapper.toEntity(returnedBlankAnswerDTO);
        assertBlankAnswerUpdatableFieldsEquals(returnedBlankAnswer, getPersistedBlankAnswer(returnedBlankAnswer));

        insertedBlankAnswer = returnedBlankAnswer;
    }

    @Test
    @Transactional
    void createBlankAnswerWithExistingId() throws Exception {
        // Create the BlankAnswer with an existing ID
        blankAnswer.setId(1L);
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlankAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnswerTextIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        blankAnswer.setAnswerText(null);

        // Create the BlankAnswer, which fails.
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        restBlankAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankAnswerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBlankAnswers() throws Exception {
        // Initialize the database
        insertedBlankAnswer = blankAnswerRepository.saveAndFlush(blankAnswer);

        // Get all the blankAnswerList
        restBlankAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blankAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerText").value(hasItem(DEFAULT_ANSWER_TEXT)))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT)));
    }

    @Test
    @Transactional
    void getBlankAnswer() throws Exception {
        // Initialize the database
        insertedBlankAnswer = blankAnswerRepository.saveAndFlush(blankAnswer);

        // Get the blankAnswer
        restBlankAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, blankAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blankAnswer.getId().intValue()))
            .andExpect(jsonPath("$.answerText").value(DEFAULT_ANSWER_TEXT))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT));
    }

    @Test
    @Transactional
    void getNonExistingBlankAnswer() throws Exception {
        // Get the blankAnswer
        restBlankAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBlankAnswer() throws Exception {
        // Initialize the database
        insertedBlankAnswer = blankAnswerRepository.saveAndFlush(blankAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blankAnswer
        BlankAnswer updatedBlankAnswer = blankAnswerRepository.findById(blankAnswer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBlankAnswer are not directly saved in db
        em.detach(updatedBlankAnswer);
        updatedBlankAnswer.answerText(UPDATED_ANSWER_TEXT).isCorrect(UPDATED_IS_CORRECT);
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(updatedBlankAnswer);

        restBlankAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blankAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blankAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBlankAnswerToMatchAllProperties(updatedBlankAnswer);
    }

    @Test
    @Transactional
    void putNonExistingBlankAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankAnswer.setId(longCount.incrementAndGet());

        // Create the BlankAnswer
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlankAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blankAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blankAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlankAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankAnswer.setId(longCount.incrementAndGet());

        // Create the BlankAnswer
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blankAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlankAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankAnswer.setId(longCount.incrementAndGet());

        // Create the BlankAnswer
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBlankAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedBlankAnswer = blankAnswerRepository.saveAndFlush(blankAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blankAnswer using partial update
        BlankAnswer partialUpdatedBlankAnswer = new BlankAnswer();
        partialUpdatedBlankAnswer.setId(blankAnswer.getId());

        restBlankAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlankAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlankAnswer))
            )
            .andExpect(status().isOk());

        // Validate the BlankAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlankAnswerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBlankAnswer, blankAnswer),
            getPersistedBlankAnswer(blankAnswer)
        );
    }

    @Test
    @Transactional
    void fullUpdateBlankAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedBlankAnswer = blankAnswerRepository.saveAndFlush(blankAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blankAnswer using partial update
        BlankAnswer partialUpdatedBlankAnswer = new BlankAnswer();
        partialUpdatedBlankAnswer.setId(blankAnswer.getId());

        partialUpdatedBlankAnswer.answerText(UPDATED_ANSWER_TEXT).isCorrect(UPDATED_IS_CORRECT);

        restBlankAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlankAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlankAnswer))
            )
            .andExpect(status().isOk());

        // Validate the BlankAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlankAnswerUpdatableFieldsEquals(partialUpdatedBlankAnswer, getPersistedBlankAnswer(partialUpdatedBlankAnswer));
    }

    @Test
    @Transactional
    void patchNonExistingBlankAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankAnswer.setId(longCount.incrementAndGet());

        // Create the BlankAnswer
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlankAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, blankAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blankAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlankAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankAnswer.setId(longCount.incrementAndGet());

        // Create the BlankAnswer
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blankAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlankAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankAnswer.setId(longCount.incrementAndGet());

        // Create the BlankAnswer
        BlankAnswerDTO blankAnswerDTO = blankAnswerMapper.toDto(blankAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankAnswerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(blankAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlankAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBlankAnswer() throws Exception {
        // Initialize the database
        insertedBlankAnswer = blankAnswerRepository.saveAndFlush(blankAnswer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the blankAnswer
        restBlankAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, blankAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return blankAnswerRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected BlankAnswer getPersistedBlankAnswer(BlankAnswer blankAnswer) {
        return blankAnswerRepository.findById(blankAnswer.getId()).orElseThrow();
    }

    protected void assertPersistedBlankAnswerToMatchAllProperties(BlankAnswer expectedBlankAnswer) {
        assertBlankAnswerAllPropertiesEquals(expectedBlankAnswer, getPersistedBlankAnswer(expectedBlankAnswer));
    }

    protected void assertPersistedBlankAnswerToMatchUpdatableProperties(BlankAnswer expectedBlankAnswer) {
        assertBlankAnswerAllUpdatablePropertiesEquals(expectedBlankAnswer, getPersistedBlankAnswer(expectedBlankAnswer));
    }
}
