package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.AnswerOptionAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.AnswerOption;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.repository.AnswerOptionRepository;
import com.skillsdoor.skillverify.service.dto.AnswerOptionDTO;
import com.skillsdoor.skillverify.service.mapper.AnswerOptionMapper;
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
 * Integration tests for the {@link AnswerOptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnswerOptionResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final String ENTITY_API_URL = "/api/answer-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Autowired
    private AnswerOptionMapper answerOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerOptionMockMvc;

    private AnswerOption answerOption;

    private AnswerOption insertedAnswerOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerOption createEntity(EntityManager em) {
        AnswerOption answerOption = new AnswerOption()
            .content(DEFAULT_CONTENT)
            .isCorrect(DEFAULT_IS_CORRECT)
            .orderIndex(DEFAULT_ORDER_INDEX);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        answerOption.setQuestion(question);
        return answerOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerOption createUpdatedEntity(EntityManager em) {
        AnswerOption updatedAnswerOption = new AnswerOption()
            .content(UPDATED_CONTENT)
            .isCorrect(UPDATED_IS_CORRECT)
            .orderIndex(UPDATED_ORDER_INDEX);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createUpdatedEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        updatedAnswerOption.setQuestion(question);
        return updatedAnswerOption;
    }

    @BeforeEach
    void initTest() {
        answerOption = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAnswerOption != null) {
            answerOptionRepository.delete(insertedAnswerOption);
            insertedAnswerOption = null;
        }
    }

    @Test
    @Transactional
    void createAnswerOption() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnswerOption
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);
        var returnedAnswerOptionDTO = om.readValue(
            restAnswerOptionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(answerOptionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnswerOptionDTO.class
        );

        // Validate the AnswerOption in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAnswerOption = answerOptionMapper.toEntity(returnedAnswerOptionDTO);
        assertAnswerOptionUpdatableFieldsEquals(returnedAnswerOption, getPersistedAnswerOption(returnedAnswerOption));

        insertedAnswerOption = returnedAnswerOption;
    }

    @Test
    @Transactional
    void createAnswerOptionWithExistingId() throws Exception {
        // Create the AnswerOption with an existing ID
        answerOption.setId(1L);
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(answerOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        answerOption.setContent(null);

        // Create the AnswerOption, which fails.
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        restAnswerOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(answerOptionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsCorrectIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        answerOption.setIsCorrect(null);

        // Create the AnswerOption, which fails.
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        restAnswerOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(answerOptionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIndexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        answerOption.setOrderIndex(null);

        // Create the AnswerOption, which fails.
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        restAnswerOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(answerOptionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnswerOptions() throws Exception {
        // Initialize the database
        insertedAnswerOption = answerOptionRepository.saveAndFlush(answerOption);

        // Get all the answerOptionList
        restAnswerOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)));
    }

    @Test
    @Transactional
    void getAnswerOption() throws Exception {
        // Initialize the database
        insertedAnswerOption = answerOptionRepository.saveAndFlush(answerOption);

        // Get the answerOption
        restAnswerOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, answerOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answerOption.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX));
    }

    @Test
    @Transactional
    void getNonExistingAnswerOption() throws Exception {
        // Get the answerOption
        restAnswerOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnswerOption() throws Exception {
        // Initialize the database
        insertedAnswerOption = answerOptionRepository.saveAndFlush(answerOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the answerOption
        AnswerOption updatedAnswerOption = answerOptionRepository.findById(answerOption.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAnswerOption are not directly saved in db
        em.detach(updatedAnswerOption);
        updatedAnswerOption.content(UPDATED_CONTENT).isCorrect(UPDATED_IS_CORRECT).orderIndex(UPDATED_ORDER_INDEX);
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(updatedAnswerOption);

        restAnswerOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, answerOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(answerOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnswerOptionToMatchAllProperties(updatedAnswerOption);
    }

    @Test
    @Transactional
    void putNonExistingAnswerOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        answerOption.setId(longCount.incrementAndGet());

        // Create the AnswerOption
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, answerOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(answerOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnswerOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        answerOption.setId(longCount.incrementAndGet());

        // Create the AnswerOption
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(answerOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnswerOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        answerOption.setId(longCount.incrementAndGet());

        // Create the AnswerOption
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerOptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(answerOptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnswerOptionWithPatch() throws Exception {
        // Initialize the database
        insertedAnswerOption = answerOptionRepository.saveAndFlush(answerOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the answerOption using partial update
        AnswerOption partialUpdatedAnswerOption = new AnswerOption();
        partialUpdatedAnswerOption.setId(answerOption.getId());

        partialUpdatedAnswerOption.content(UPDATED_CONTENT).orderIndex(UPDATED_ORDER_INDEX);

        restAnswerOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswerOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnswerOption))
            )
            .andExpect(status().isOk());

        // Validate the AnswerOption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnswerOptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnswerOption, answerOption),
            getPersistedAnswerOption(answerOption)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnswerOptionWithPatch() throws Exception {
        // Initialize the database
        insertedAnswerOption = answerOptionRepository.saveAndFlush(answerOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the answerOption using partial update
        AnswerOption partialUpdatedAnswerOption = new AnswerOption();
        partialUpdatedAnswerOption.setId(answerOption.getId());

        partialUpdatedAnswerOption.content(UPDATED_CONTENT).isCorrect(UPDATED_IS_CORRECT).orderIndex(UPDATED_ORDER_INDEX);

        restAnswerOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswerOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnswerOption))
            )
            .andExpect(status().isOk());

        // Validate the AnswerOption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnswerOptionUpdatableFieldsEquals(partialUpdatedAnswerOption, getPersistedAnswerOption(partialUpdatedAnswerOption));
    }

    @Test
    @Transactional
    void patchNonExistingAnswerOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        answerOption.setId(longCount.incrementAndGet());

        // Create the AnswerOption
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, answerOptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(answerOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnswerOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        answerOption.setId(longCount.incrementAndGet());

        // Create the AnswerOption
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(answerOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnswerOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        answerOption.setId(longCount.incrementAndGet());

        // Create the AnswerOption
        AnswerOptionDTO answerOptionDTO = answerOptionMapper.toDto(answerOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerOptionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(answerOptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnswerOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnswerOption() throws Exception {
        // Initialize the database
        insertedAnswerOption = answerOptionRepository.saveAndFlush(answerOption);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the answerOption
        restAnswerOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, answerOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return answerOptionRepository.count();
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

    protected AnswerOption getPersistedAnswerOption(AnswerOption answerOption) {
        return answerOptionRepository.findById(answerOption.getId()).orElseThrow();
    }

    protected void assertPersistedAnswerOptionToMatchAllProperties(AnswerOption expectedAnswerOption) {
        assertAnswerOptionAllPropertiesEquals(expectedAnswerOption, getPersistedAnswerOption(expectedAnswerOption));
    }

    protected void assertPersistedAnswerOptionToMatchUpdatableProperties(AnswerOption expectedAnswerOption) {
        assertAnswerOptionAllUpdatablePropertiesEquals(expectedAnswerOption, getPersistedAnswerOption(expectedAnswerOption));
    }
}
