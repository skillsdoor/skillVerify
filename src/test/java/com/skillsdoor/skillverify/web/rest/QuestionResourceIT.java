package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.QuestionAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.repository.QuestionRepository;
import com.skillsdoor.skillverify.service.dto.QuestionDTO;
import com.skillsdoor.skillverify.service.mapper.QuestionMapper;
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
 * Integration tests for the {@link QuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final String ENTITY_API_URL = "/api/questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionMockMvc;

    private Question question;

    private Question insertedQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createEntity(EntityManager em) {
        Question question = new Question()
            .content(DEFAULT_CONTENT)
            .explanation(DEFAULT_EXPLANATION)
            .points(DEFAULT_POINTS)
            .orderIndex(DEFAULT_ORDER_INDEX);
        // Add required entity
        Exercise exercise;
        if (TestUtil.findAll(em, Exercise.class).isEmpty()) {
            exercise = ExerciseResourceIT.createEntity(em);
            em.persist(exercise);
            em.flush();
        } else {
            exercise = TestUtil.findAll(em, Exercise.class).get(0);
        }
        question.setExercise(exercise);
        return question;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createUpdatedEntity(EntityManager em) {
        Question updatedQuestion = new Question()
            .content(UPDATED_CONTENT)
            .explanation(UPDATED_EXPLANATION)
            .points(UPDATED_POINTS)
            .orderIndex(UPDATED_ORDER_INDEX);
        // Add required entity
        Exercise exercise;
        if (TestUtil.findAll(em, Exercise.class).isEmpty()) {
            exercise = ExerciseResourceIT.createUpdatedEntity(em);
            em.persist(exercise);
            em.flush();
        } else {
            exercise = TestUtil.findAll(em, Exercise.class).get(0);
        }
        updatedQuestion.setExercise(exercise);
        return updatedQuestion;
    }

    @BeforeEach
    void initTest() {
        question = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedQuestion != null) {
            questionRepository.delete(insertedQuestion);
            insertedQuestion = null;
        }
    }

    @Test
    @Transactional
    void createQuestion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);
        var returnedQuestionDTO = om.readValue(
            restQuestionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(questionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            QuestionDTO.class
        );

        // Validate the Question in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedQuestion = questionMapper.toEntity(returnedQuestionDTO);
        assertQuestionUpdatableFieldsEquals(returnedQuestion, getPersistedQuestion(returnedQuestion));

        insertedQuestion = returnedQuestion;
    }

    @Test
    @Transactional
    void createQuestionWithExistingId() throws Exception {
        // Create the Question with an existing ID
        question.setId(1L);
        QuestionDTO questionDTO = questionMapper.toDto(question);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPointsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        question.setPoints(null);

        // Create the Question, which fails.
        QuestionDTO questionDTO = questionMapper.toDto(question);

        restQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIndexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        question.setOrderIndex(null);

        // Create the Question, which fails.
        QuestionDTO questionDTO = questionMapper.toDto(question);

        restQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuestions() throws Exception {
        // Initialize the database
        insertedQuestion = questionRepository.saveAndFlush(question);

        // Get all the questionList
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)));
    }

    @Test
    @Transactional
    void getQuestion() throws Exception {
        // Initialize the database
        insertedQuestion = questionRepository.saveAndFlush(question);

        // Get the question
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX));
    }

    @Test
    @Transactional
    void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuestion() throws Exception {
        // Initialize the database
        insertedQuestion = questionRepository.saveAndFlush(question);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQuestion are not directly saved in db
        em.detach(updatedQuestion);
        updatedQuestion.content(UPDATED_CONTENT).explanation(UPDATED_EXPLANATION).points(UPDATED_POINTS).orderIndex(UPDATED_ORDER_INDEX);
        QuestionDTO questionDTO = questionMapper.toDto(updatedQuestion);

        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(questionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedQuestionToMatchAllProperties(updatedQuestion);
    }

    @Test
    @Transactional
    void putNonExistingQuestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        question.setId(longCount.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        question.setId(longCount.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        question.setId(longCount.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(questionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuestionWithPatch() throws Exception {
        // Initialize the database
        insertedQuestion = questionRepository.saveAndFlush(question);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the question using partial update
        Question partialUpdatedQuestion = new Question();
        partialUpdatedQuestion.setId(question.getId());

        partialUpdatedQuestion.points(UPDATED_POINTS);

        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuestion))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuestionUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedQuestion, question), getPersistedQuestion(question));
    }

    @Test
    @Transactional
    void fullUpdateQuestionWithPatch() throws Exception {
        // Initialize the database
        insertedQuestion = questionRepository.saveAndFlush(question);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the question using partial update
        Question partialUpdatedQuestion = new Question();
        partialUpdatedQuestion.setId(question.getId());

        partialUpdatedQuestion
            .content(UPDATED_CONTENT)
            .explanation(UPDATED_EXPLANATION)
            .points(UPDATED_POINTS)
            .orderIndex(UPDATED_ORDER_INDEX);

        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuestion))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuestionUpdatableFieldsEquals(partialUpdatedQuestion, getPersistedQuestion(partialUpdatedQuestion));
    }

    @Test
    @Transactional
    void patchNonExistingQuestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        question.setId(longCount.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        question.setId(longCount.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        question.setId(longCount.incrementAndGet());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(questionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Question in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuestion() throws Exception {
        // Initialize the database
        insertedQuestion = questionRepository.saveAndFlush(question);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the question
        restQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, question.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return questionRepository.count();
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

    protected Question getPersistedQuestion(Question question) {
        return questionRepository.findById(question.getId()).orElseThrow();
    }

    protected void assertPersistedQuestionToMatchAllProperties(Question expectedQuestion) {
        assertQuestionAllPropertiesEquals(expectedQuestion, getPersistedQuestion(expectedQuestion));
    }

    protected void assertPersistedQuestionToMatchUpdatableProperties(Question expectedQuestion) {
        assertQuestionAllUpdatablePropertiesEquals(expectedQuestion, getPersistedQuestion(expectedQuestion));
    }
}
