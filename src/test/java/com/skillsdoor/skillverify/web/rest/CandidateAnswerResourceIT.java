package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.CandidateAnswerAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.domain.ExamSession;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.repository.CandidateAnswerRepository;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.CandidateAnswerMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CandidateAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CandidateAnswerResourceIT {

    private static final String DEFAULT_ANSWER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_TEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_SELECTED_OPTION_ID = 1L;
    private static final Long UPDATED_SELECTED_OPTION_ID = 2L;

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final Integer DEFAULT_POINTS_AWARDED = 1;
    private static final Integer UPDATED_POINTS_AWARDED = 2;

    private static final Instant DEFAULT_ANSWERED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANSWERED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/candidate-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CandidateAnswerRepository candidateAnswerRepository;

    @Autowired
    private CandidateAnswerMapper candidateAnswerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateAnswerMockMvc;

    private CandidateAnswer candidateAnswer;

    private CandidateAnswer insertedCandidateAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateAnswer createEntity(EntityManager em) {
        CandidateAnswer candidateAnswer = new CandidateAnswer()
            .answerText(DEFAULT_ANSWER_TEXT)
            .selectedOptionId(DEFAULT_SELECTED_OPTION_ID)
            .isCorrect(DEFAULT_IS_CORRECT)
            .pointsAwarded(DEFAULT_POINTS_AWARDED)
            .answeredAt(DEFAULT_ANSWERED_AT);
        // Add required entity
        ExamSession examSession;
        if (TestUtil.findAll(em, ExamSession.class).isEmpty()) {
            examSession = ExamSessionResourceIT.createEntity(em);
            em.persist(examSession);
            em.flush();
        } else {
            examSession = TestUtil.findAll(em, ExamSession.class).get(0);
        }
        candidateAnswer.setExamSession(examSession);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        candidateAnswer.setQuestion(question);
        return candidateAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateAnswer createUpdatedEntity(EntityManager em) {
        CandidateAnswer updatedCandidateAnswer = new CandidateAnswer()
            .answerText(UPDATED_ANSWER_TEXT)
            .selectedOptionId(UPDATED_SELECTED_OPTION_ID)
            .isCorrect(UPDATED_IS_CORRECT)
            .pointsAwarded(UPDATED_POINTS_AWARDED)
            .answeredAt(UPDATED_ANSWERED_AT);
        // Add required entity
        ExamSession examSession;
        if (TestUtil.findAll(em, ExamSession.class).isEmpty()) {
            examSession = ExamSessionResourceIT.createUpdatedEntity(em);
            em.persist(examSession);
            em.flush();
        } else {
            examSession = TestUtil.findAll(em, ExamSession.class).get(0);
        }
        updatedCandidateAnswer.setExamSession(examSession);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createUpdatedEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        updatedCandidateAnswer.setQuestion(question);
        return updatedCandidateAnswer;
    }

    @BeforeEach
    void initTest() {
        candidateAnswer = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCandidateAnswer != null) {
            candidateAnswerRepository.delete(insertedCandidateAnswer);
            insertedCandidateAnswer = null;
        }
    }

    @Test
    @Transactional
    void createCandidateAnswer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CandidateAnswer
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);
        var returnedCandidateAnswerDTO = om.readValue(
            restCandidateAnswerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateAnswerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CandidateAnswerDTO.class
        );

        // Validate the CandidateAnswer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCandidateAnswer = candidateAnswerMapper.toEntity(returnedCandidateAnswerDTO);
        assertCandidateAnswerUpdatableFieldsEquals(returnedCandidateAnswer, getPersistedCandidateAnswer(returnedCandidateAnswer));

        insertedCandidateAnswer = returnedCandidateAnswer;
    }

    @Test
    @Transactional
    void createCandidateAnswerWithExistingId() throws Exception {
        // Create the CandidateAnswer with an existing ID
        candidateAnswer.setId(1L);
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidateAnswers() throws Exception {
        // Initialize the database
        insertedCandidateAnswer = candidateAnswerRepository.saveAndFlush(candidateAnswer);

        // Get all the candidateAnswerList
        restCandidateAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerText").value(hasItem(DEFAULT_ANSWER_TEXT)))
            .andExpect(jsonPath("$.[*].selectedOptionId").value(hasItem(DEFAULT_SELECTED_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT)))
            .andExpect(jsonPath("$.[*].pointsAwarded").value(hasItem(DEFAULT_POINTS_AWARDED)))
            .andExpect(jsonPath("$.[*].answeredAt").value(hasItem(DEFAULT_ANSWERED_AT.toString())));
    }

    @Test
    @Transactional
    void getCandidateAnswer() throws Exception {
        // Initialize the database
        insertedCandidateAnswer = candidateAnswerRepository.saveAndFlush(candidateAnswer);

        // Get the candidateAnswer
        restCandidateAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, candidateAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidateAnswer.getId().intValue()))
            .andExpect(jsonPath("$.answerText").value(DEFAULT_ANSWER_TEXT))
            .andExpect(jsonPath("$.selectedOptionId").value(DEFAULT_SELECTED_OPTION_ID.intValue()))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT))
            .andExpect(jsonPath("$.pointsAwarded").value(DEFAULT_POINTS_AWARDED))
            .andExpect(jsonPath("$.answeredAt").value(DEFAULT_ANSWERED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCandidateAnswer() throws Exception {
        // Get the candidateAnswer
        restCandidateAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidateAnswer() throws Exception {
        // Initialize the database
        insertedCandidateAnswer = candidateAnswerRepository.saveAndFlush(candidateAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidateAnswer
        CandidateAnswer updatedCandidateAnswer = candidateAnswerRepository.findById(candidateAnswer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCandidateAnswer are not directly saved in db
        em.detach(updatedCandidateAnswer);
        updatedCandidateAnswer
            .answerText(UPDATED_ANSWER_TEXT)
            .selectedOptionId(UPDATED_SELECTED_OPTION_ID)
            .isCorrect(UPDATED_IS_CORRECT)
            .pointsAwarded(UPDATED_POINTS_AWARDED)
            .answeredAt(UPDATED_ANSWERED_AT);
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(updatedCandidateAnswer);

        restCandidateAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidateAnswerDTO))
            )
            .andExpect(status().isOk());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCandidateAnswerToMatchAllProperties(updatedCandidateAnswer);
    }

    @Test
    @Transactional
    void putNonExistingCandidateAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidateAnswer.setId(longCount.incrementAndGet());

        // Create the CandidateAnswer
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateAnswerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidateAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidateAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidateAnswer.setId(longCount.incrementAndGet());

        // Create the CandidateAnswer
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidateAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidateAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidateAnswer.setId(longCount.incrementAndGet());

        // Create the CandidateAnswer
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidateAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedCandidateAnswer = candidateAnswerRepository.saveAndFlush(candidateAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidateAnswer using partial update
        CandidateAnswer partialUpdatedCandidateAnswer = new CandidateAnswer();
        partialUpdatedCandidateAnswer.setId(candidateAnswer.getId());

        partialUpdatedCandidateAnswer.answerText(UPDATED_ANSWER_TEXT).isCorrect(UPDATED_IS_CORRECT).answeredAt(UPDATED_ANSWERED_AT);

        restCandidateAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidateAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidateAnswer))
            )
            .andExpect(status().isOk());

        // Validate the CandidateAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidateAnswerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCandidateAnswer, candidateAnswer),
            getPersistedCandidateAnswer(candidateAnswer)
        );
    }

    @Test
    @Transactional
    void fullUpdateCandidateAnswerWithPatch() throws Exception {
        // Initialize the database
        insertedCandidateAnswer = candidateAnswerRepository.saveAndFlush(candidateAnswer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidateAnswer using partial update
        CandidateAnswer partialUpdatedCandidateAnswer = new CandidateAnswer();
        partialUpdatedCandidateAnswer.setId(candidateAnswer.getId());

        partialUpdatedCandidateAnswer
            .answerText(UPDATED_ANSWER_TEXT)
            .selectedOptionId(UPDATED_SELECTED_OPTION_ID)
            .isCorrect(UPDATED_IS_CORRECT)
            .pointsAwarded(UPDATED_POINTS_AWARDED)
            .answeredAt(UPDATED_ANSWERED_AT);

        restCandidateAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidateAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidateAnswer))
            )
            .andExpect(status().isOk());

        // Validate the CandidateAnswer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidateAnswerUpdatableFieldsEquals(
            partialUpdatedCandidateAnswer,
            getPersistedCandidateAnswer(partialUpdatedCandidateAnswer)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCandidateAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidateAnswer.setId(longCount.incrementAndGet());

        // Create the CandidateAnswer
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidateAnswerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidateAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidateAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidateAnswer.setId(longCount.incrementAndGet());

        // Create the CandidateAnswer
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidateAnswerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidateAnswer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidateAnswer.setId(longCount.incrementAndGet());

        // Create the CandidateAnswer
        CandidateAnswerDTO candidateAnswerDTO = candidateAnswerMapper.toDto(candidateAnswer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAnswerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(candidateAnswerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidateAnswer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidateAnswer() throws Exception {
        // Initialize the database
        insertedCandidateAnswer = candidateAnswerRepository.saveAndFlush(candidateAnswer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the candidateAnswer
        restCandidateAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidateAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return candidateAnswerRepository.count();
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

    protected CandidateAnswer getPersistedCandidateAnswer(CandidateAnswer candidateAnswer) {
        return candidateAnswerRepository.findById(candidateAnswer.getId()).orElseThrow();
    }

    protected void assertPersistedCandidateAnswerToMatchAllProperties(CandidateAnswer expectedCandidateAnswer) {
        assertCandidateAnswerAllPropertiesEquals(expectedCandidateAnswer, getPersistedCandidateAnswer(expectedCandidateAnswer));
    }

    protected void assertPersistedCandidateAnswerToMatchUpdatableProperties(CandidateAnswer expectedCandidateAnswer) {
        assertCandidateAnswerAllUpdatablePropertiesEquals(expectedCandidateAnswer, getPersistedCandidateAnswer(expectedCandidateAnswer));
    }
}
