package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.BindAnswerAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.BindAnswer;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.repository.BindAnswerRepository;
import com.skillsdoor.skillverify.service.dto.BindAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.BindAnswerMapper;
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
 * Integration tests for the {@link BindAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BindAnswerResourceIT {

    private static final Long DEFAULT_LEFT_ITEM_ID = 1L;
    private static final Long UPDATED_LEFT_ITEM_ID = 2L;

    private static final Long DEFAULT_RIGHT_ITEM_ID = 1L;
    private static final Long UPDATED_RIGHT_ITEM_ID = 2L;

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final String ENTITY_API_URL = "/api/bind-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BindAnswerRepository bindAnswerRepository;

    @Autowired
    private BindAnswerMapper bindAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBindAnswerMockMvc;

    private BindAnswer bindAnswer;

    private BindAnswer insertedBindAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BindAnswer createEntity(EntityManager em) {
        BindAnswer bindAnswer = new BindAnswer()
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
        bindAnswer.setCandidateAnswer(candidateAnswer);
        return bindAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BindAnswer createUpdatedEntity(EntityManager em) {
        BindAnswer updatedBindAnswer = new BindAnswer()
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
        updatedBindAnswer.setCandidateAnswer(candidateAnswer);
        return updatedBindAnswer;
    }

    @BeforeEach
    void initTest() {
        bindAnswer = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedBindAnswer != null) {
            bindAnswerRepository.delete(insertedBindAnswer);
            insertedBindAnswer = null;
        }
    }

    @Test
    @Transactional
    void createBindAnswer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BindAnswer
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);
        var returnedBindAnswerDTO = om.readValue(
            restBindAnswerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindAnswerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BindAnswerDTO.class
        );

        // Validate the BindAnswer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBindAnswer = bindAnswerMapper.toEntity(returnedBindAnswerDTO);
        assertBindAnswerUpdatableFieldsEquals(returnedBindAnswer, getPersistedBindAnswer(returnedBindAnswer));

        insertedBindAnswer = returnedBindAnswer;
    }

    @Test
    @Transactional
    void createBindAnswerWithExistingId() throws Exception {
        // Create the BindAnswer with an existing ID
        bindAnswer.setId(1L);
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBindAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLeftItemIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bindAnswer.setLeftItemId(null);

        // Create the BindAnswer, which fails.
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        restBindAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindAnswerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRightItemIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bindAnswer.setRightItemId(null);

        // Create the BindAnswer, which fails.
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        restBindAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindAnswerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBindAnswers() throws Exception {
        // Initialize the database
        insertedBindAnswer = bindAnswerRepository.saveAndFlush(bindAnswer);

        // Get all the bindAnswerList
        restBindAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bindAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].leftItemId").value(hasItem(DEFAULT_LEFT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].rightItemId").value(hasItem(DEFAULT_RIGHT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT)));
    }

    @Test
    @Transactional
    void getBindAnswer() throws Exception {
        // Initialize the database
        insertedBindAnswer = bindAnswerRepository.saveAndFlush(bindAnswer);

        // Get the bindAnswer
        restBindAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, bindAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bindAnswer.getId().intValue()))
            .andExpect(jsonPath("$.leftItemId").value(DEFAULT_LEFT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.rightItemId").value(DEFAULT_RIGHT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT));
    }

    @Test
    @Transactional
    void getNonExistingBindAnswer() throws Exception {
        // Get the bindAnswer
        restBindAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBindAnswer() throws Exception {
        // Initialize the database
        insertedBindAnswer = bindAnswerRepository.saveAndFlush(bindAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindAnswer
        BindAnswer updatedBindAnswer = bindAnswerRepository.findById(bindAnswer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBindAnswer are not directly saved in db
        em.detach(updatedBindAnswer);
        updatedBindAnswer.leftItemId(UPDATED_LEFT_ITEM_ID).rightItemId(UPDATED_RIGHT_ITEM_ID).isCorrect(UPDATED_IS_CORRECT);
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(updatedBindAnswer);

        restBindAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bindAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBindAnswerToMatchAllProperties(updatedBindAnswer);
    }

    @Test
    @Transactional
    void putNonExistingBindAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindAnswer.setId(longCount.incrementAndGet());

        // Create the BindAnswer
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBindAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bindAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBindAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindAnswer.setId(longCount.incrementAndGet());

        // Create the BindAnswer
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBindAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindAnswer.setId(longCount.incrementAndGet());

        // Create the BindAnswer
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBindAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedBindAnswer = bindAnswerRepository.saveAndFlush(bindAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindAnswer using partial update
        BindAnswer partialUpdatedBindAnswer = new BindAnswer();
        partialUpdatedBindAnswer.setId(bindAnswer.getId());

        partialUpdatedBindAnswer.leftItemId(UPDATED_LEFT_ITEM_ID).rightItemId(UPDATED_RIGHT_ITEM_ID).isCorrect(UPDATED_IS_CORRECT);

        restBindAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBindAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBindAnswer))
            )
            .andExpect(status().isOk());

        // Validate the BindAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBindAnswerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBindAnswer, bindAnswer),
            getPersistedBindAnswer(bindAnswer)
        );
    }

    @Test
    @Transactional
    void fullUpdateBindAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedBindAnswer = bindAnswerRepository.saveAndFlush(bindAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindAnswer using partial update
        BindAnswer partialUpdatedBindAnswer = new BindAnswer();
        partialUpdatedBindAnswer.setId(bindAnswer.getId());

        partialUpdatedBindAnswer.leftItemId(UPDATED_LEFT_ITEM_ID).rightItemId(UPDATED_RIGHT_ITEM_ID).isCorrect(UPDATED_IS_CORRECT);

        restBindAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBindAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBindAnswer))
            )
            .andExpect(status().isOk());

        // Validate the BindAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBindAnswerUpdatableFieldsEquals(partialUpdatedBindAnswer, getPersistedBindAnswer(partialUpdatedBindAnswer));
    }

    @Test
    @Transactional
    void patchNonExistingBindAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindAnswer.setId(longCount.incrementAndGet());

        // Create the BindAnswer
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBindAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bindAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bindAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBindAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindAnswer.setId(longCount.incrementAndGet());

        // Create the BindAnswer
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bindAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBindAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindAnswer.setId(longCount.incrementAndGet());

        // Create the BindAnswer
        BindAnswerDTO bindAnswerDTO = bindAnswerMapper.toDto(bindAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindAnswerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bindAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BindAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBindAnswer() throws Exception {
        // Initialize the database
        insertedBindAnswer = bindAnswerRepository.saveAndFlush(bindAnswer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bindAnswer
        restBindAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, bindAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bindAnswerRepository.count();
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

    protected BindAnswer getPersistedBindAnswer(BindAnswer bindAnswer) {
        return bindAnswerRepository.findById(bindAnswer.getId()).orElseThrow();
    }

    protected void assertPersistedBindAnswerToMatchAllProperties(BindAnswer expectedBindAnswer) {
        assertBindAnswerAllPropertiesEquals(expectedBindAnswer, getPersistedBindAnswer(expectedBindAnswer));
    }

    protected void assertPersistedBindAnswerToMatchUpdatableProperties(BindAnswer expectedBindAnswer) {
        assertBindAnswerAllUpdatablePropertiesEquals(expectedBindAnswer, getPersistedBindAnswer(expectedBindAnswer));
    }
}
