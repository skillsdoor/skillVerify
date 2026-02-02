package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.BindListAnswerAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.BindListAnswer;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.repository.BindListAnswerRepository;
import com.skillsdoor.skillverify.service.dto.BindListAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.BindListAnswerMapper;
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
 * Integration tests for the {@link BindListAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BindListAnswerResourceIT {

    private static final Long DEFAULT_LEFT_ITEM_ID = 1L;
    private static final Long UPDATED_LEFT_ITEM_ID = 2L;

    private static final Long DEFAULT_RIGHT_ITEM_ID = 1L;
    private static final Long UPDATED_RIGHT_ITEM_ID = 2L;

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final String ENTITY_API_URL = "/api/bind-list-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BindListAnswerRepository bindListAnswerRepository;

    @Autowired
    private BindListAnswerMapper bindListAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBindListAnswerMockMvc;

    private BindListAnswer bindListAnswer;

    private BindListAnswer insertedBindListAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BindListAnswer createEntity(EntityManager em) {
        BindListAnswer bindListAnswer = new BindListAnswer()
            .leftItemId(DEFAULT_LEFT_ITEM_ID)
            .rightItemId(DEFAULT_RIGHT_ITEM_ID)
            .isCorrect(DEFAULT_IS_CORRECT);
        // Add required entity
        CandidateAnswer candidateAnswer;
        if (TestUtil.findAll(em, CandidateAnswer.class).isEmpty()) {
            candidateAnswer = CandidateAnswerResourceIT.createEntity(em);
            em.persist(candidateAnswer);
            em.flush();
        } else {
            candidateAnswer = TestUtil.findAll(em, CandidateAnswer.class).get(0);
        }
        bindListAnswer.setCandidateAnswer(candidateAnswer);
        return bindListAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BindListAnswer createUpdatedEntity(EntityManager em) {
        BindListAnswer updatedBindListAnswer = new BindListAnswer()
            .leftItemId(UPDATED_LEFT_ITEM_ID)
            .rightItemId(UPDATED_RIGHT_ITEM_ID)
            .isCorrect(UPDATED_IS_CORRECT);
        // Add required entity
        CandidateAnswer candidateAnswer;
        if (TestUtil.findAll(em, CandidateAnswer.class).isEmpty()) {
            candidateAnswer = CandidateAnswerResourceIT.createUpdatedEntity(em);
            em.persist(candidateAnswer);
            em.flush();
        } else {
            candidateAnswer = TestUtil.findAll(em, CandidateAnswer.class).get(0);
        }
        updatedBindListAnswer.setCandidateAnswer(candidateAnswer);
        return updatedBindListAnswer;
    }

    @BeforeEach
    void initTest() {
        bindListAnswer = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedBindListAnswer != null) {
            bindListAnswerRepository.delete(insertedBindListAnswer);
            insertedBindListAnswer = null;
        }
    }

    @Test
    @Transactional
    void createBindListAnswer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BindListAnswer
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);
        var returnedBindListAnswerDTO = om.readValue(
            restBindListAnswerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListAnswerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BindListAnswerDTO.class
        );

        // Validate the BindListAnswer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBindListAnswer = bindListAnswerMapper.toEntity(returnedBindListAnswerDTO);
        assertBindListAnswerUpdatableFieldsEquals(returnedBindListAnswer, getPersistedBindListAnswer(returnedBindListAnswer));

        insertedBindListAnswer = returnedBindListAnswer;
    }

    @Test
    @Transactional
    void createBindListAnswerWithExistingId() throws Exception {
        // Create the BindListAnswer with an existing ID
        bindListAnswer.setId(1L);
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBindListAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLeftItemIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bindListAnswer.setLeftItemId(null);

        // Create the BindListAnswer, which fails.
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        restBindListAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListAnswerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRightItemIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bindListAnswer.setRightItemId(null);

        // Create the BindListAnswer, which fails.
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        restBindListAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListAnswerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBindListAnswers() throws Exception {
        // Initialize the database
        insertedBindListAnswer = bindListAnswerRepository.saveAndFlush(bindListAnswer);

        // Get all the bindListAnswerList
        restBindListAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bindListAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].leftItemId").value(hasItem(DEFAULT_LEFT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].rightItemId").value(hasItem(DEFAULT_RIGHT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT)));
    }

    @Test
    @Transactional
    void getBindListAnswer() throws Exception {
        // Initialize the database
        insertedBindListAnswer = bindListAnswerRepository.saveAndFlush(bindListAnswer);

        // Get the bindListAnswer
        restBindListAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, bindListAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bindListAnswer.getId().intValue()))
            .andExpect(jsonPath("$.leftItemId").value(DEFAULT_LEFT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.rightItemId").value(DEFAULT_RIGHT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT));
    }

    @Test
    @Transactional
    void getNonExistingBindListAnswer() throws Exception {
        // Get the bindListAnswer
        restBindListAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBindListAnswer() throws Exception {
        // Initialize the database
        insertedBindListAnswer = bindListAnswerRepository.saveAndFlush(bindListAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindListAnswer
        BindListAnswer updatedBindListAnswer = bindListAnswerRepository.findById(bindListAnswer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBindListAnswer are not directly saved in db
        em.detach(updatedBindListAnswer);
        updatedBindListAnswer.leftItemId(UPDATED_LEFT_ITEM_ID).rightItemId(UPDATED_RIGHT_ITEM_ID).isCorrect(UPDATED_IS_CORRECT);
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(updatedBindListAnswer);

        restBindListAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bindListAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindListAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBindListAnswerToMatchAllProperties(updatedBindListAnswer);
    }

    @Test
    @Transactional
    void putNonExistingBindListAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindListAnswer.setId(longCount.incrementAndGet());

        // Create the BindListAnswer
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBindListAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bindListAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindListAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBindListAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindListAnswer.setId(longCount.incrementAndGet());

        // Create the BindListAnswer
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindListAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBindListAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindListAnswer.setId(longCount.incrementAndGet());

        // Create the BindListAnswer
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBindListAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedBindListAnswer = bindListAnswerRepository.saveAndFlush(bindListAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindListAnswer using partial update
        BindListAnswer partialUpdatedBindListAnswer = new BindListAnswer();
        partialUpdatedBindListAnswer.setId(bindListAnswer.getId());

        partialUpdatedBindListAnswer.isCorrect(UPDATED_IS_CORRECT);

        restBindListAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBindListAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBindListAnswer))
            )
            .andExpect(status().isOk());

        // Validate the BindListAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBindListAnswerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBindListAnswer, bindListAnswer),
            getPersistedBindListAnswer(bindListAnswer)
        );
    }

    @Test
    @Transactional
    void fullUpdateBindListAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedBindListAnswer = bindListAnswerRepository.saveAndFlush(bindListAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindListAnswer using partial update
        BindListAnswer partialUpdatedBindListAnswer = new BindListAnswer();
        partialUpdatedBindListAnswer.setId(bindListAnswer.getId());

        partialUpdatedBindListAnswer.leftItemId(UPDATED_LEFT_ITEM_ID).rightItemId(UPDATED_RIGHT_ITEM_ID).isCorrect(UPDATED_IS_CORRECT);

        restBindListAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBindListAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBindListAnswer))
            )
            .andExpect(status().isOk());

        // Validate the BindListAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBindListAnswerUpdatableFieldsEquals(partialUpdatedBindListAnswer, getPersistedBindListAnswer(partialUpdatedBindListAnswer));
    }

    @Test
    @Transactional
    void patchNonExistingBindListAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindListAnswer.setId(longCount.incrementAndGet());

        // Create the BindListAnswer
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBindListAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bindListAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bindListAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBindListAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindListAnswer.setId(longCount.incrementAndGet());

        // Create the BindListAnswer
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bindListAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBindListAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindListAnswer.setId(longCount.incrementAndGet());

        // Create the BindListAnswer
        BindListAnswerDTO bindListAnswerDTO = bindListAnswerMapper.toDto(bindListAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListAnswerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bindListAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BindListAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBindListAnswer() throws Exception {
        // Initialize the database
        insertedBindListAnswer = bindListAnswerRepository.saveAndFlush(bindListAnswer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bindListAnswer
        restBindListAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, bindListAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bindListAnswerRepository.count();
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

    protected BindListAnswer getPersistedBindListAnswer(BindListAnswer bindListAnswer) {
        return bindListAnswerRepository.findById(bindListAnswer.getId()).orElseThrow();
    }

    protected void assertPersistedBindListAnswerToMatchAllProperties(BindListAnswer expectedBindListAnswer) {
        assertBindListAnswerAllPropertiesEquals(expectedBindListAnswer, getPersistedBindListAnswer(expectedBindListAnswer));
    }

    protected void assertPersistedBindListAnswerToMatchUpdatableProperties(BindListAnswer expectedBindListAnswer) {
        assertBindListAnswerAllUpdatablePropertiesEquals(expectedBindListAnswer, getPersistedBindListAnswer(expectedBindListAnswer));
    }
}
