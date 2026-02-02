package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.FillInTheBlankAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.FillInTheBlank;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.repository.FillInTheBlankRepository;
import com.skillsdoor.skillverify.service.dto.FillInTheBlankDTO;
import com.skillsdoor.skillverify.service.mapper.FillInTheBlankMapper;
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
 * Integration tests for the {@link FillInTheBlankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FillInTheBlankResourceIT {

    private static final String DEFAULT_TEXT_WITH_BLANKS = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_WITH_BLANKS = "BBBBBBBBBB";

    private static final Integer DEFAULT_BLANK_INDEX = 1;
    private static final Integer UPDATED_BLANK_INDEX = 2;

    private static final String DEFAULT_CORRECT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_CORRECT_ANSWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CASE_SENSITIVE = false;
    private static final Boolean UPDATED_CASE_SENSITIVE = true;

    private static final String ENTITY_API_URL = "/api/fill-in-the-blanks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FillInTheBlankRepository fillInTheBlankRepository;

    @Autowired
    private FillInTheBlankMapper fillInTheBlankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFillInTheBlankMockMvc;

    private FillInTheBlank fillInTheBlank;

    private FillInTheBlank insertedFillInTheBlank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FillInTheBlank createEntity(EntityManager em) {
        FillInTheBlank fillInTheBlank = new FillInTheBlank()
            .textWithBlanks(DEFAULT_TEXT_WITH_BLANKS)
            .blankIndex(DEFAULT_BLANK_INDEX)
            .correctAnswer(DEFAULT_CORRECT_ANSWER)
            .caseSensitive(DEFAULT_CASE_SENSITIVE);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        fillInTheBlank.setQuestion(question);
        return fillInTheBlank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FillInTheBlank createUpdatedEntity(EntityManager em) {
        FillInTheBlank updatedFillInTheBlank = new FillInTheBlank()
            .textWithBlanks(UPDATED_TEXT_WITH_BLANKS)
            .blankIndex(UPDATED_BLANK_INDEX)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .caseSensitive(UPDATED_CASE_SENSITIVE);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createUpdatedEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        updatedFillInTheBlank.setQuestion(question);
        return updatedFillInTheBlank;
    }

    @BeforeEach
    void initTest() {
        fillInTheBlank = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedFillInTheBlank != null) {
            fillInTheBlankRepository.delete(insertedFillInTheBlank);
            insertedFillInTheBlank = null;
        }
    }

    @Test
    @Transactional
    void createFillInTheBlank() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FillInTheBlank
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);
        var returnedFillInTheBlankDTO = om.readValue(
            restFillInTheBlankMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fillInTheBlankDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FillInTheBlankDTO.class
        );

        // Validate the FillInTheBlank in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFillInTheBlank = fillInTheBlankMapper.toEntity(returnedFillInTheBlankDTO);
        assertFillInTheBlankUpdatableFieldsEquals(returnedFillInTheBlank, getPersistedFillInTheBlank(returnedFillInTheBlank));

        insertedFillInTheBlank = returnedFillInTheBlank;
    }

    @Test
    @Transactional
    void createFillInTheBlankWithExistingId() throws Exception {
        // Create the FillInTheBlank with an existing ID
        fillInTheBlank.setId(1L);
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFillInTheBlankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fillInTheBlankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBlankIndexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fillInTheBlank.setBlankIndex(null);

        // Create the FillInTheBlank, which fails.
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        restFillInTheBlankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fillInTheBlankDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorrectAnswerIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fillInTheBlank.setCorrectAnswer(null);

        // Create the FillInTheBlank, which fails.
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        restFillInTheBlankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fillInTheBlankDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCaseSensitiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fillInTheBlank.setCaseSensitive(null);

        // Create the FillInTheBlank, which fails.
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        restFillInTheBlankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fillInTheBlankDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFillInTheBlanks() throws Exception {
        // Initialize the database
        insertedFillInTheBlank = fillInTheBlankRepository.saveAndFlush(fillInTheBlank);

        // Get all the fillInTheBlankList
        restFillInTheBlankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fillInTheBlank.getId().intValue())))
            .andExpect(jsonPath("$.[*].textWithBlanks").value(hasItem(DEFAULT_TEXT_WITH_BLANKS)))
            .andExpect(jsonPath("$.[*].blankIndex").value(hasItem(DEFAULT_BLANK_INDEX)))
            .andExpect(jsonPath("$.[*].correctAnswer").value(hasItem(DEFAULT_CORRECT_ANSWER)))
            .andExpect(jsonPath("$.[*].caseSensitive").value(hasItem(DEFAULT_CASE_SENSITIVE)));
    }

    @Test
    @Transactional
    void getFillInTheBlank() throws Exception {
        // Initialize the database
        insertedFillInTheBlank = fillInTheBlankRepository.saveAndFlush(fillInTheBlank);

        // Get the fillInTheBlank
        restFillInTheBlankMockMvc
            .perform(get(ENTITY_API_URL_ID, fillInTheBlank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fillInTheBlank.getId().intValue()))
            .andExpect(jsonPath("$.textWithBlanks").value(DEFAULT_TEXT_WITH_BLANKS))
            .andExpect(jsonPath("$.blankIndex").value(DEFAULT_BLANK_INDEX))
            .andExpect(jsonPath("$.correctAnswer").value(DEFAULT_CORRECT_ANSWER))
            .andExpect(jsonPath("$.caseSensitive").value(DEFAULT_CASE_SENSITIVE));
    }

    @Test
    @Transactional
    void getNonExistingFillInTheBlank() throws Exception {
        // Get the fillInTheBlank
        restFillInTheBlankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFillInTheBlank() throws Exception {
        // Initialize the database
        insertedFillInTheBlank = fillInTheBlankRepository.saveAndFlush(fillInTheBlank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fillInTheBlank
        FillInTheBlank updatedFillInTheBlank = fillInTheBlankRepository.findById(fillInTheBlank.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFillInTheBlank are not directly saved in db
        em.detach(updatedFillInTheBlank);
        updatedFillInTheBlank
            .textWithBlanks(UPDATED_TEXT_WITH_BLANKS)
            .blankIndex(UPDATED_BLANK_INDEX)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .caseSensitive(UPDATED_CASE_SENSITIVE);
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(updatedFillInTheBlank);

        restFillInTheBlankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fillInTheBlankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fillInTheBlankDTO))
            )
            .andExpect(status().isOk());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFillInTheBlankToMatchAllProperties(updatedFillInTheBlank);
    }

    @Test
    @Transactional
    void putNonExistingFillInTheBlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fillInTheBlank.setId(longCount.incrementAndGet());

        // Create the FillInTheBlank
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFillInTheBlankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fillInTheBlankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fillInTheBlankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFillInTheBlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fillInTheBlank.setId(longCount.incrementAndGet());

        // Create the FillInTheBlank
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFillInTheBlankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fillInTheBlankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFillInTheBlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fillInTheBlank.setId(longCount.incrementAndGet());

        // Create the FillInTheBlank
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFillInTheBlankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fillInTheBlankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFillInTheBlankWithPatch() throws Exception {
        // Initialize the database
        insertedFillInTheBlank = fillInTheBlankRepository.saveAndFlush(fillInTheBlank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fillInTheBlank using partial update
        FillInTheBlank partialUpdatedFillInTheBlank = new FillInTheBlank();
        partialUpdatedFillInTheBlank.setId(fillInTheBlank.getId());

        partialUpdatedFillInTheBlank
            .textWithBlanks(UPDATED_TEXT_WITH_BLANKS)
            .blankIndex(UPDATED_BLANK_INDEX)
            .correctAnswer(UPDATED_CORRECT_ANSWER);

        restFillInTheBlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFillInTheBlank.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFillInTheBlank))
            )
            .andExpect(status().isOk());

        // Validate the FillInTheBlank in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFillInTheBlankUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFillInTheBlank, fillInTheBlank),
            getPersistedFillInTheBlank(fillInTheBlank)
        );
    }

    @Test
    @Transactional
    void fullUpdateFillInTheBlankWithPatch() throws Exception {
        // Initialize the database
        insertedFillInTheBlank = fillInTheBlankRepository.saveAndFlush(fillInTheBlank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fillInTheBlank using partial update
        FillInTheBlank partialUpdatedFillInTheBlank = new FillInTheBlank();
        partialUpdatedFillInTheBlank.setId(fillInTheBlank.getId());

        partialUpdatedFillInTheBlank
            .textWithBlanks(UPDATED_TEXT_WITH_BLANKS)
            .blankIndex(UPDATED_BLANK_INDEX)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .caseSensitive(UPDATED_CASE_SENSITIVE);

        restFillInTheBlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFillInTheBlank.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFillInTheBlank))
            )
            .andExpect(status().isOk());

        // Validate the FillInTheBlank in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFillInTheBlankUpdatableFieldsEquals(partialUpdatedFillInTheBlank, getPersistedFillInTheBlank(partialUpdatedFillInTheBlank));
    }

    @Test
    @Transactional
    void patchNonExistingFillInTheBlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fillInTheBlank.setId(longCount.incrementAndGet());

        // Create the FillInTheBlank
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFillInTheBlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fillInTheBlankDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fillInTheBlankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFillInTheBlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fillInTheBlank.setId(longCount.incrementAndGet());

        // Create the FillInTheBlank
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFillInTheBlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fillInTheBlankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFillInTheBlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fillInTheBlank.setId(longCount.incrementAndGet());

        // Create the FillInTheBlank
        FillInTheBlankDTO fillInTheBlankDTO = fillInTheBlankMapper.toDto(fillInTheBlank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFillInTheBlankMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fillInTheBlankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FillInTheBlank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFillInTheBlank() throws Exception {
        // Initialize the database
        insertedFillInTheBlank = fillInTheBlankRepository.saveAndFlush(fillInTheBlank);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fillInTheBlank
        restFillInTheBlankMockMvc
            .perform(delete(ENTITY_API_URL_ID, fillInTheBlank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fillInTheBlankRepository.count();
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

    protected FillInTheBlank getPersistedFillInTheBlank(FillInTheBlank fillInTheBlank) {
        return fillInTheBlankRepository.findById(fillInTheBlank.getId()).orElseThrow();
    }

    protected void assertPersistedFillInTheBlankToMatchAllProperties(FillInTheBlank expectedFillInTheBlank) {
        assertFillInTheBlankAllPropertiesEquals(expectedFillInTheBlank, getPersistedFillInTheBlank(expectedFillInTheBlank));
    }

    protected void assertPersistedFillInTheBlankToMatchUpdatableProperties(FillInTheBlank expectedFillInTheBlank) {
        assertFillInTheBlankAllUpdatablePropertiesEquals(expectedFillInTheBlank, getPersistedFillInTheBlank(expectedFillInTheBlank));
    }
}
