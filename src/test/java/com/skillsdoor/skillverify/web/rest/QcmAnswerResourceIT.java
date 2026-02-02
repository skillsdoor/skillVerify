package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.QcmAnswerAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.domain.QcmAnswer;
import com.skillsdoor.skillverify.domain.QcmOption;
import com.skillsdoor.skillverify.repository.QcmAnswerRepository;
import com.skillsdoor.skillverify.service.dto.QcmAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.QcmAnswerMapper;
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
 * Integration tests for the {@link QcmAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QcmAnswerResourceIT {

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final String ENTITY_API_URL = "/api/qcm-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private QcmAnswerRepository qcmAnswerRepository;

    @Autowired
    private QcmAnswerMapper qcmAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQcmAnswerMockMvc;

    private QcmAnswer qcmAnswer;

    private QcmAnswer insertedQcmAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QcmAnswer createEntity(EntityManager em) {
        QcmAnswer qcmAnswer = new QcmAnswer().isCorrect(DEFAULT_IS_CORRECT);
        // Add required entity
        CandidateAnswer candidateAnswer;
        if (TestUtil.findAll(em, CandidateAnswer.class).isEmpty()) {
            candidateAnswer = CandidateAnswerResourceIT.createEntity(em);
            em.persist(candidateAnswer);
            em.flush();
        } else {
            candidateAnswer = TestUtil.findAll(em, CandidateAnswer.class).get(0);
        }
        qcmAnswer.setCandidateAnswer(candidateAnswer);
        // Add required entity
        QcmOption qcmOption;
        if (TestUtil.findAll(em, QcmOption.class).isEmpty()) {
            qcmOption = QcmOptionResourceIT.createEntity(em);
            em.persist(qcmOption);
            em.flush();
        } else {
            qcmOption = TestUtil.findAll(em, QcmOption.class).get(0);
        }
        qcmAnswer.setQcmOption(qcmOption);
        return qcmAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QcmAnswer createUpdatedEntity(EntityManager em) {
        QcmAnswer updatedQcmAnswer = new QcmAnswer().isCorrect(UPDATED_IS_CORRECT);
        // Add required entity
        CandidateAnswer candidateAnswer;
        if (TestUtil.findAll(em, CandidateAnswer.class).isEmpty()) {
            candidateAnswer = CandidateAnswerResourceIT.createUpdatedEntity(em);
            em.persist(candidateAnswer);
            em.flush();
        } else {
            candidateAnswer = TestUtil.findAll(em, CandidateAnswer.class).get(0);
        }
        updatedQcmAnswer.setCandidateAnswer(candidateAnswer);
        // Add required entity
        QcmOption qcmOption;
        if (TestUtil.findAll(em, QcmOption.class).isEmpty()) {
            qcmOption = QcmOptionResourceIT.createUpdatedEntity(em);
            em.persist(qcmOption);
            em.flush();
        } else {
            qcmOption = TestUtil.findAll(em, QcmOption.class).get(0);
        }
        updatedQcmAnswer.setQcmOption(qcmOption);
        return updatedQcmAnswer;
    }

    @BeforeEach
    void initTest() {
        qcmAnswer = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedQcmAnswer != null) {
            qcmAnswerRepository.delete(insertedQcmAnswer);
            insertedQcmAnswer = null;
        }
    }

    @Test
    @Transactional
    void createQcmAnswer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the QcmAnswer
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);
        var returnedQcmAnswerDTO = om.readValue(
            restQcmAnswerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmAnswerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            QcmAnswerDTO.class
        );

        // Validate the QcmAnswer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedQcmAnswer = qcmAnswerMapper.toEntity(returnedQcmAnswerDTO);
        assertQcmAnswerUpdatableFieldsEquals(returnedQcmAnswer, getPersistedQcmAnswer(returnedQcmAnswer));

        insertedQcmAnswer = returnedQcmAnswer;
    }

    @Test
    @Transactional
    void createQcmAnswerWithExistingId() throws Exception {
        // Create the QcmAnswer with an existing ID
        qcmAnswer.setId(1L);
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQcmAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQcmAnswers() throws Exception {
        // Initialize the database
        insertedQcmAnswer = qcmAnswerRepository.saveAndFlush(qcmAnswer);

        // Get all the qcmAnswerList
        restQcmAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qcmAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT)));
    }

    @Test
    @Transactional
    void getQcmAnswer() throws Exception {
        // Initialize the database
        insertedQcmAnswer = qcmAnswerRepository.saveAndFlush(qcmAnswer);

        // Get the qcmAnswer
        restQcmAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, qcmAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qcmAnswer.getId().intValue()))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT));
    }

    @Test
    @Transactional
    void getNonExistingQcmAnswer() throws Exception {
        // Get the qcmAnswer
        restQcmAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQcmAnswer() throws Exception {
        // Initialize the database
        insertedQcmAnswer = qcmAnswerRepository.saveAndFlush(qcmAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qcmAnswer
        QcmAnswer updatedQcmAnswer = qcmAnswerRepository.findById(qcmAnswer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQcmAnswer are not directly saved in db
        em.detach(updatedQcmAnswer);
        updatedQcmAnswer.isCorrect(UPDATED_IS_CORRECT);
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(updatedQcmAnswer);

        restQcmAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qcmAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qcmAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedQcmAnswerToMatchAllProperties(updatedQcmAnswer);
    }

    @Test
    @Transactional
    void putNonExistingQcmAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmAnswer.setId(longCount.incrementAndGet());

        // Create the QcmAnswer
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qcmAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qcmAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQcmAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmAnswer.setId(longCount.incrementAndGet());

        // Create the QcmAnswer
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qcmAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQcmAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmAnswer.setId(longCount.incrementAndGet());

        // Create the QcmAnswer
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQcmAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedQcmAnswer = qcmAnswerRepository.saveAndFlush(qcmAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qcmAnswer using partial update
        QcmAnswer partialUpdatedQcmAnswer = new QcmAnswer();
        partialUpdatedQcmAnswer.setId(qcmAnswer.getId());

        partialUpdatedQcmAnswer.isCorrect(UPDATED_IS_CORRECT);

        restQcmAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcmAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQcmAnswer))
            )
            .andExpect(status().isOk());

        // Validate the QcmAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQcmAnswerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedQcmAnswer, qcmAnswer),
            getPersistedQcmAnswer(qcmAnswer)
        );
    }

    @Test
    @Transactional
    void fullUpdateQcmAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedQcmAnswer = qcmAnswerRepository.saveAndFlush(qcmAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qcmAnswer using partial update
        QcmAnswer partialUpdatedQcmAnswer = new QcmAnswer();
        partialUpdatedQcmAnswer.setId(qcmAnswer.getId());

        partialUpdatedQcmAnswer.isCorrect(UPDATED_IS_CORRECT);

        restQcmAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcmAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQcmAnswer))
            )
            .andExpect(status().isOk());

        // Validate the QcmAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQcmAnswerUpdatableFieldsEquals(partialUpdatedQcmAnswer, getPersistedQcmAnswer(partialUpdatedQcmAnswer));
    }

    @Test
    @Transactional
    void patchNonExistingQcmAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmAnswer.setId(longCount.incrementAndGet());

        // Create the QcmAnswer
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qcmAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(qcmAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQcmAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmAnswer.setId(longCount.incrementAndGet());

        // Create the QcmAnswer
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(qcmAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQcmAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmAnswer.setId(longCount.incrementAndGet());

        // Create the QcmAnswer
        QcmAnswerDTO qcmAnswerDTO = qcmAnswerMapper.toDto(qcmAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmAnswerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(qcmAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QcmAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQcmAnswer() throws Exception {
        // Initialize the database
        insertedQcmAnswer = qcmAnswerRepository.saveAndFlush(qcmAnswer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the qcmAnswer
        restQcmAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, qcmAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return qcmAnswerRepository.count();
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

    protected QcmAnswer getPersistedQcmAnswer(QcmAnswer qcmAnswer) {
        return qcmAnswerRepository.findById(qcmAnswer.getId()).orElseThrow();
    }

    protected void assertPersistedQcmAnswerToMatchAllProperties(QcmAnswer expectedQcmAnswer) {
        assertQcmAnswerAllPropertiesEquals(expectedQcmAnswer, getPersistedQcmAnswer(expectedQcmAnswer));
    }

    protected void assertPersistedQcmAnswerToMatchUpdatableProperties(QcmAnswer expectedQcmAnswer) {
        assertQcmAnswerAllUpdatablePropertiesEquals(expectedQcmAnswer, getPersistedQcmAnswer(expectedQcmAnswer));
    }
}
