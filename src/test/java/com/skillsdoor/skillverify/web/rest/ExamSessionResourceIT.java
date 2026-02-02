package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.ExamSessionAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.Candidate;
import com.skillsdoor.skillverify.domain.Exam;
import com.skillsdoor.skillverify.domain.ExamSession;
import com.skillsdoor.skillverify.domain.enumeration.SubmissionStatus;
import com.skillsdoor.skillverify.repository.ExamSessionRepository;
import com.skillsdoor.skillverify.service.dto.ExamSessionDTO;
import com.skillsdoor.skillverify.service.mapper.ExamSessionMapper;
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
 * Integration tests for the {@link ExamSessionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamSessionResourceIT {

    private static final Instant DEFAULT_STARTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SUBMITTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMITTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SubmissionStatus DEFAULT_STATUS = SubmissionStatus.NOT_STARTED;
    private static final SubmissionStatus UPDATED_STATUS = SubmissionStatus.IN_PROGRESS;

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final Integer DEFAULT_TOTAL_POINTS = 1;
    private static final Integer UPDATED_TOTAL_POINTS = 2;

    private static final Integer DEFAULT_TIME_SPENT = 1;
    private static final Integer UPDATED_TIME_SPENT = 2;

    private static final String ENTITY_API_URL = "/api/exam-sessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExamSessionRepository examSessionRepository;

    @Autowired
    private ExamSessionMapper examSessionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamSessionMockMvc;

    private ExamSession examSession;

    private ExamSession insertedExamSession;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamSession createEntity(EntityManager em) {
        ExamSession examSession = new ExamSession()
            .startedAt(DEFAULT_STARTED_AT)
            .submittedAt(DEFAULT_SUBMITTED_AT)
            .status(DEFAULT_STATUS)
            .score(DEFAULT_SCORE)
            .totalPoints(DEFAULT_TOTAL_POINTS)
            .timeSpent(DEFAULT_TIME_SPENT);
        // Add required entity
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            candidate = CandidateResourceIT.createEntity(em);
            em.persist(candidate);
            em.flush();
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        examSession.setCandidate(candidate);
        // Add required entity
        Exam exam;
        if (TestUtil.findAll(em, Exam.class).isEmpty()) {
            exam = ExamResourceIT.createEntity();
            em.persist(exam);
            em.flush();
        } else {
            exam = TestUtil.findAll(em, Exam.class).get(0);
        }
        examSession.setExam(exam);
        return examSession;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamSession createUpdatedEntity(EntityManager em) {
        ExamSession updatedExamSession = new ExamSession()
            .startedAt(UPDATED_STARTED_AT)
            .submittedAt(UPDATED_SUBMITTED_AT)
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .totalPoints(UPDATED_TOTAL_POINTS)
            .timeSpent(UPDATED_TIME_SPENT);
        // Add required entity
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            candidate = CandidateResourceIT.createUpdatedEntity(em);
            em.persist(candidate);
            em.flush();
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        updatedExamSession.setCandidate(candidate);
        // Add required entity
        Exam exam;
        if (TestUtil.findAll(em, Exam.class).isEmpty()) {
            exam = ExamResourceIT.createUpdatedEntity();
            em.persist(exam);
            em.flush();
        } else {
            exam = TestUtil.findAll(em, Exam.class).get(0);
        }
        updatedExamSession.setExam(exam);
        return updatedExamSession;
    }

    @BeforeEach
    void initTest() {
        examSession = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedExamSession != null) {
            examSessionRepository.delete(insertedExamSession);
            insertedExamSession = null;
        }
    }

    @Test
    @Transactional
    void createExamSession() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ExamSession
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);
        var returnedExamSessionDTO = om.readValue(
            restExamSessionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examSessionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ExamSessionDTO.class
        );

        // Validate the ExamSession in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedExamSession = examSessionMapper.toEntity(returnedExamSessionDTO);
        assertExamSessionUpdatableFieldsEquals(returnedExamSession, getPersistedExamSession(returnedExamSession));

        insertedExamSession = returnedExamSession;
    }

    @Test
    @Transactional
    void createExamSessionWithExistingId() throws Exception {
        // Create the ExamSession with an existing ID
        examSession.setId(1L);
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        examSession.setStartedAt(null);

        // Create the ExamSession, which fails.
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        restExamSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examSessionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        examSession.setStatus(null);

        // Create the ExamSession, which fails.
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        restExamSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examSessionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExamSessions() throws Exception {
        // Initialize the database
        insertedExamSession = examSessionRepository.saveAndFlush(examSession);

        // Get all the examSessionList
        restExamSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(DEFAULT_STARTED_AT.toString())))
            .andExpect(jsonPath("$.[*].submittedAt").value(hasItem(DEFAULT_SUBMITTED_AT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS)))
            .andExpect(jsonPath("$.[*].timeSpent").value(hasItem(DEFAULT_TIME_SPENT)));
    }

    @Test
    @Transactional
    void getExamSession() throws Exception {
        // Initialize the database
        insertedExamSession = examSessionRepository.saveAndFlush(examSession);

        // Get the examSession
        restExamSessionMockMvc
            .perform(get(ENTITY_API_URL_ID, examSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examSession.getId().intValue()))
            .andExpect(jsonPath("$.startedAt").value(DEFAULT_STARTED_AT.toString()))
            .andExpect(jsonPath("$.submittedAt").value(DEFAULT_SUBMITTED_AT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS))
            .andExpect(jsonPath("$.timeSpent").value(DEFAULT_TIME_SPENT));
    }

    @Test
    @Transactional
    void getNonExistingExamSession() throws Exception {
        // Get the examSession
        restExamSessionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExamSession() throws Exception {
        // Initialize the database
        insertedExamSession = examSessionRepository.saveAndFlush(examSession);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examSession
        ExamSession updatedExamSession = examSessionRepository.findById(examSession.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExamSession are not directly saved in db
        em.detach(updatedExamSession);
        updatedExamSession
            .startedAt(UPDATED_STARTED_AT)
            .submittedAt(UPDATED_SUBMITTED_AT)
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .totalPoints(UPDATED_TOTAL_POINTS)
            .timeSpent(UPDATED_TIME_SPENT);
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(updatedExamSession);

        restExamSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(examSessionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExamSessionToMatchAllProperties(updatedExamSession);
    }

    @Test
    @Transactional
    void putNonExistingExamSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examSession.setId(longCount.incrementAndGet());

        // Create the ExamSession
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(examSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExamSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examSession.setId(longCount.incrementAndGet());

        // Create the ExamSession
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(examSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExamSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examSession.setId(longCount.incrementAndGet());

        // Create the ExamSession
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamSessionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examSessionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamSessionWithPatch() throws Exception {
        // Initialize the database
        insertedExamSession = examSessionRepository.saveAndFlush(examSession);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examSession using partial update
        ExamSession partialUpdatedExamSession = new ExamSession();
        partialUpdatedExamSession.setId(examSession.getId());

        partialUpdatedExamSession.status(UPDATED_STATUS).score(UPDATED_SCORE).totalPoints(UPDATED_TOTAL_POINTS);

        restExamSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExamSession))
            )
            .andExpect(status().isOk());

        // Validate the ExamSession in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExamSessionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedExamSession, examSession),
            getPersistedExamSession(examSession)
        );
    }

    @Test
    @Transactional
    void fullUpdateExamSessionWithPatch() throws Exception {
        // Initialize the database
        insertedExamSession = examSessionRepository.saveAndFlush(examSession);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examSession using partial update
        ExamSession partialUpdatedExamSession = new ExamSession();
        partialUpdatedExamSession.setId(examSession.getId());

        partialUpdatedExamSession
            .startedAt(UPDATED_STARTED_AT)
            .submittedAt(UPDATED_SUBMITTED_AT)
            .status(UPDATED_STATUS)
            .score(UPDATED_SCORE)
            .totalPoints(UPDATED_TOTAL_POINTS)
            .timeSpent(UPDATED_TIME_SPENT);

        restExamSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExamSession))
            )
            .andExpect(status().isOk());

        // Validate the ExamSession in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExamSessionUpdatableFieldsEquals(partialUpdatedExamSession, getPersistedExamSession(partialUpdatedExamSession));
    }

    @Test
    @Transactional
    void patchNonExistingExamSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examSession.setId(longCount.incrementAndGet());

        // Create the ExamSession
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examSessionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(examSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExamSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examSession.setId(longCount.incrementAndGet());

        // Create the ExamSession
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(examSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExamSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examSession.setId(longCount.incrementAndGet());

        // Create the ExamSession
        ExamSessionDTO examSessionDTO = examSessionMapper.toDto(examSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamSessionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(examSessionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExamSession() throws Exception {
        // Initialize the database
        insertedExamSession = examSessionRepository.saveAndFlush(examSession);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the examSession
        restExamSessionMockMvc
            .perform(delete(ENTITY_API_URL_ID, examSession.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return examSessionRepository.count();
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

    protected ExamSession getPersistedExamSession(ExamSession examSession) {
        return examSessionRepository.findById(examSession.getId()).orElseThrow();
    }

    protected void assertPersistedExamSessionToMatchAllProperties(ExamSession expectedExamSession) {
        assertExamSessionAllPropertiesEquals(expectedExamSession, getPersistedExamSession(expectedExamSession));
    }

    protected void assertPersistedExamSessionToMatchUpdatableProperties(ExamSession expectedExamSession) {
        assertExamSessionAllUpdatablePropertiesEquals(expectedExamSession, getPersistedExamSession(expectedExamSession));
    }
}
