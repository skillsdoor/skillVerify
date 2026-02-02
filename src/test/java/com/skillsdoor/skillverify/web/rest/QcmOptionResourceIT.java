package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.QcmOptionAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.QcmOption;
import com.skillsdoor.skillverify.repository.QcmOptionRepository;
import com.skillsdoor.skillverify.service.dto.QcmOptionDTO;
import com.skillsdoor.skillverify.service.mapper.QcmOptionMapper;
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
 * Integration tests for the {@link QcmOptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QcmOptionResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final String ENTITY_API_URL = "/api/qcm-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private QcmOptionRepository qcmOptionRepository;

    @Autowired
    private QcmOptionMapper qcmOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQcmOptionMockMvc;

    private QcmOption qcmOption;

    private QcmOption insertedQcmOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QcmOption createEntity(EntityManager em) {
        QcmOption qcmOption = new QcmOption().content(DEFAULT_CONTENT).isCorrect(DEFAULT_IS_CORRECT).orderIndex(DEFAULT_ORDER_INDEX);
        // Add required entity
        Exercise exercise;
        if (TestUtil.findAll(em, Exercise.class).isEmpty()) {
            exercise = ExerciseResourceIT.createEntity(em);
            em.persist(exercise);
            em.flush();
        } else {
            exercise = TestUtil.findAll(em, Exercise.class).get(0);
        }
        qcmOption.setExercise(exercise);
        return qcmOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QcmOption createUpdatedEntity(EntityManager em) {
        QcmOption updatedQcmOption = new QcmOption().content(UPDATED_CONTENT).isCorrect(UPDATED_IS_CORRECT).orderIndex(UPDATED_ORDER_INDEX);
        // Add required entity
        Exercise exercise;
        if (TestUtil.findAll(em, Exercise.class).isEmpty()) {
            exercise = ExerciseResourceIT.createUpdatedEntity(em);
            em.persist(exercise);
            em.flush();
        } else {
            exercise = TestUtil.findAll(em, Exercise.class).get(0);
        }
        updatedQcmOption.setExercise(exercise);
        return updatedQcmOption;
    }

    @BeforeEach
    void initTest() {
        qcmOption = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedQcmOption != null) {
            qcmOptionRepository.delete(insertedQcmOption);
            insertedQcmOption = null;
        }
    }

    @Test
    @Transactional
    void createQcmOption() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the QcmOption
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);
        var returnedQcmOptionDTO = om.readValue(
            restQcmOptionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmOptionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            QcmOptionDTO.class
        );

        // Validate the QcmOption in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedQcmOption = qcmOptionMapper.toEntity(returnedQcmOptionDTO);
        assertQcmOptionUpdatableFieldsEquals(returnedQcmOption, getPersistedQcmOption(returnedQcmOption));

        insertedQcmOption = returnedQcmOption;
    }

    @Test
    @Transactional
    void createQcmOptionWithExistingId() throws Exception {
        // Create the QcmOption with an existing ID
        qcmOption.setId(1L);
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQcmOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        qcmOption.setContent(null);

        // Create the QcmOption, which fails.
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        restQcmOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmOptionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsCorrectIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        qcmOption.setIsCorrect(null);

        // Create the QcmOption, which fails.
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        restQcmOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmOptionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIndexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        qcmOption.setOrderIndex(null);

        // Create the QcmOption, which fails.
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        restQcmOptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmOptionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQcmOptions() throws Exception {
        // Initialize the database
        insertedQcmOption = qcmOptionRepository.saveAndFlush(qcmOption);

        // Get all the qcmOptionList
        restQcmOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qcmOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)));
    }

    @Test
    @Transactional
    void getQcmOption() throws Exception {
        // Initialize the database
        insertedQcmOption = qcmOptionRepository.saveAndFlush(qcmOption);

        // Get the qcmOption
        restQcmOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, qcmOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qcmOption.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX));
    }

    @Test
    @Transactional
    void getNonExistingQcmOption() throws Exception {
        // Get the qcmOption
        restQcmOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQcmOption() throws Exception {
        // Initialize the database
        insertedQcmOption = qcmOptionRepository.saveAndFlush(qcmOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qcmOption
        QcmOption updatedQcmOption = qcmOptionRepository.findById(qcmOption.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQcmOption are not directly saved in db
        em.detach(updatedQcmOption);
        updatedQcmOption.content(UPDATED_CONTENT).isCorrect(UPDATED_IS_CORRECT).orderIndex(UPDATED_ORDER_INDEX);
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(updatedQcmOption);

        restQcmOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qcmOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qcmOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedQcmOptionToMatchAllProperties(updatedQcmOption);
    }

    @Test
    @Transactional
    void putNonExistingQcmOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmOption.setId(longCount.incrementAndGet());

        // Create the QcmOption
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qcmOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qcmOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQcmOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmOption.setId(longCount.incrementAndGet());

        // Create the QcmOption
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qcmOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQcmOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmOption.setId(longCount.incrementAndGet());

        // Create the QcmOption
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmOptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qcmOptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQcmOptionWithPatch() throws Exception {
        // Initialize the database
        insertedQcmOption = qcmOptionRepository.saveAndFlush(qcmOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qcmOption using partial update
        QcmOption partialUpdatedQcmOption = new QcmOption();
        partialUpdatedQcmOption.setId(qcmOption.getId());

        partialUpdatedQcmOption.content(UPDATED_CONTENT).orderIndex(UPDATED_ORDER_INDEX);

        restQcmOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcmOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQcmOption))
            )
            .andExpect(status().isOk());

        // Validate the QcmOption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQcmOptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedQcmOption, qcmOption),
            getPersistedQcmOption(qcmOption)
        );
    }

    @Test
    @Transactional
    void fullUpdateQcmOptionWithPatch() throws Exception {
        // Initialize the database
        insertedQcmOption = qcmOptionRepository.saveAndFlush(qcmOption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qcmOption using partial update
        QcmOption partialUpdatedQcmOption = new QcmOption();
        partialUpdatedQcmOption.setId(qcmOption.getId());

        partialUpdatedQcmOption.content(UPDATED_CONTENT).isCorrect(UPDATED_IS_CORRECT).orderIndex(UPDATED_ORDER_INDEX);

        restQcmOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQcmOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQcmOption))
            )
            .andExpect(status().isOk());

        // Validate the QcmOption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQcmOptionUpdatableFieldsEquals(partialUpdatedQcmOption, getPersistedQcmOption(partialUpdatedQcmOption));
    }

    @Test
    @Transactional
    void patchNonExistingQcmOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmOption.setId(longCount.incrementAndGet());

        // Create the QcmOption
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQcmOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qcmOptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(qcmOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQcmOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmOption.setId(longCount.incrementAndGet());

        // Create the QcmOption
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(qcmOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQcmOption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qcmOption.setId(longCount.incrementAndGet());

        // Create the QcmOption
        QcmOptionDTO qcmOptionDTO = qcmOptionMapper.toDto(qcmOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQcmOptionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(qcmOptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QcmOption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQcmOption() throws Exception {
        // Initialize the database
        insertedQcmOption = qcmOptionRepository.saveAndFlush(qcmOption);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the qcmOption
        restQcmOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, qcmOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return qcmOptionRepository.count();
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

    protected QcmOption getPersistedQcmOption(QcmOption qcmOption) {
        return qcmOptionRepository.findById(qcmOption.getId()).orElseThrow();
    }

    protected void assertPersistedQcmOptionToMatchAllProperties(QcmOption expectedQcmOption) {
        assertQcmOptionAllPropertiesEquals(expectedQcmOption, getPersistedQcmOption(expectedQcmOption));
    }

    protected void assertPersistedQcmOptionToMatchUpdatableProperties(QcmOption expectedQcmOption) {
        assertQcmOptionAllUpdatablePropertiesEquals(expectedQcmOption, getPersistedQcmOption(expectedQcmOption));
    }
}
