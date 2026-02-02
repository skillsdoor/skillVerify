package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.BlankSpaceAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.BlankSpace;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.repository.BlankSpaceRepository;
import com.skillsdoor.skillverify.service.dto.BlankSpaceDTO;
import com.skillsdoor.skillverify.service.mapper.BlankSpaceMapper;
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
 * Integration tests for the {@link BlankSpaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlankSpaceResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String DEFAULT_CORRECT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_CORRECT_ANSWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CASE_SENSITIVE = false;
    private static final Boolean UPDATED_CASE_SENSITIVE = true;

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final String ENTITY_API_URL = "/api/blank-spaces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BlankSpaceRepository blankSpaceRepository;

    @Autowired
    private BlankSpaceMapper blankSpaceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlankSpaceMockMvc;

    private BlankSpace blankSpace;

    private BlankSpace insertedBlankSpace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlankSpace createEntity(EntityManager em) {
        BlankSpace blankSpace = new BlankSpace()
            .position(DEFAULT_POSITION)
            .correctAnswer(DEFAULT_CORRECT_ANSWER)
            .caseSensitive(DEFAULT_CASE_SENSITIVE)
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
        blankSpace.setExercise(exercise);
        return blankSpace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlankSpace createUpdatedEntity(EntityManager em) {
        BlankSpace updatedBlankSpace = new BlankSpace()
            .position(UPDATED_POSITION)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .caseSensitive(UPDATED_CASE_SENSITIVE)
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
        updatedBlankSpace.setExercise(exercise);
        return updatedBlankSpace;
    }

    @BeforeEach
    void initTest() {
        blankSpace = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedBlankSpace != null) {
            blankSpaceRepository.delete(insertedBlankSpace);
            insertedBlankSpace = null;
        }
    }

    @Test
    @Transactional
    void createBlankSpace() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BlankSpace
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);
        var returnedBlankSpaceDTO = om.readValue(
            restBlankSpaceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankSpaceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BlankSpaceDTO.class
        );

        // Validate the BlankSpace in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBlankSpace = blankSpaceMapper.toEntity(returnedBlankSpaceDTO);
        assertBlankSpaceUpdatableFieldsEquals(returnedBlankSpace, getPersistedBlankSpace(returnedBlankSpace));

        insertedBlankSpace = returnedBlankSpace;
    }

    @Test
    @Transactional
    void createBlankSpaceWithExistingId() throws Exception {
        // Create the BlankSpace with an existing ID
        blankSpace.setId(1L);
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlankSpaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankSpaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPositionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        blankSpace.setPosition(null);

        // Create the BlankSpace, which fails.
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        restBlankSpaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankSpaceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorrectAnswerIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        blankSpace.setCorrectAnswer(null);

        // Create the BlankSpace, which fails.
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        restBlankSpaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankSpaceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCaseSensitiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        blankSpace.setCaseSensitive(null);

        // Create the BlankSpace, which fails.
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        restBlankSpaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankSpaceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIndexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        blankSpace.setOrderIndex(null);

        // Create the BlankSpace, which fails.
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        restBlankSpaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankSpaceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBlankSpaces() throws Exception {
        // Initialize the database
        insertedBlankSpace = blankSpaceRepository.saveAndFlush(blankSpace);

        // Get all the blankSpaceList
        restBlankSpaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blankSpace.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].correctAnswer").value(hasItem(DEFAULT_CORRECT_ANSWER)))
            .andExpect(jsonPath("$.[*].caseSensitive").value(hasItem(DEFAULT_CASE_SENSITIVE)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)));
    }

    @Test
    @Transactional
    void getBlankSpace() throws Exception {
        // Initialize the database
        insertedBlankSpace = blankSpaceRepository.saveAndFlush(blankSpace);

        // Get the blankSpace
        restBlankSpaceMockMvc
            .perform(get(ENTITY_API_URL_ID, blankSpace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blankSpace.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.correctAnswer").value(DEFAULT_CORRECT_ANSWER))
            .andExpect(jsonPath("$.caseSensitive").value(DEFAULT_CASE_SENSITIVE))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX));
    }

    @Test
    @Transactional
    void getNonExistingBlankSpace() throws Exception {
        // Get the blankSpace
        restBlankSpaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBlankSpace() throws Exception {
        // Initialize the database
        insertedBlankSpace = blankSpaceRepository.saveAndFlush(blankSpace);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blankSpace
        BlankSpace updatedBlankSpace = blankSpaceRepository.findById(blankSpace.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBlankSpace are not directly saved in db
        em.detach(updatedBlankSpace);
        updatedBlankSpace
            .position(UPDATED_POSITION)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .caseSensitive(UPDATED_CASE_SENSITIVE)
            .orderIndex(UPDATED_ORDER_INDEX);
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(updatedBlankSpace);

        restBlankSpaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blankSpaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blankSpaceDTO))
            )
            .andExpect(status().isOk());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBlankSpaceToMatchAllProperties(updatedBlankSpace);
    }

    @Test
    @Transactional
    void putNonExistingBlankSpace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankSpace.setId(longCount.incrementAndGet());

        // Create the BlankSpace
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlankSpaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blankSpaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blankSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlankSpace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankSpace.setId(longCount.incrementAndGet());

        // Create the BlankSpace
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankSpaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blankSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlankSpace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankSpace.setId(longCount.incrementAndGet());

        // Create the BlankSpace
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankSpaceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blankSpaceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBlankSpaceWithPatch() throws Exception {
        // Initialize the database
        insertedBlankSpace = blankSpaceRepository.saveAndFlush(blankSpace);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blankSpace using partial update
        BlankSpace partialUpdatedBlankSpace = new BlankSpace();
        partialUpdatedBlankSpace.setId(blankSpace.getId());

        partialUpdatedBlankSpace.position(UPDATED_POSITION).correctAnswer(UPDATED_CORRECT_ANSWER).caseSensitive(UPDATED_CASE_SENSITIVE);

        restBlankSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlankSpace.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlankSpace))
            )
            .andExpect(status().isOk());

        // Validate the BlankSpace in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlankSpaceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBlankSpace, blankSpace),
            getPersistedBlankSpace(blankSpace)
        );
    }

    @Test
    @Transactional
    void fullUpdateBlankSpaceWithPatch() throws Exception {
        // Initialize the database
        insertedBlankSpace = blankSpaceRepository.saveAndFlush(blankSpace);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blankSpace using partial update
        BlankSpace partialUpdatedBlankSpace = new BlankSpace();
        partialUpdatedBlankSpace.setId(blankSpace.getId());

        partialUpdatedBlankSpace
            .position(UPDATED_POSITION)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .caseSensitive(UPDATED_CASE_SENSITIVE)
            .orderIndex(UPDATED_ORDER_INDEX);

        restBlankSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlankSpace.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlankSpace))
            )
            .andExpect(status().isOk());

        // Validate the BlankSpace in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlankSpaceUpdatableFieldsEquals(partialUpdatedBlankSpace, getPersistedBlankSpace(partialUpdatedBlankSpace));
    }

    @Test
    @Transactional
    void patchNonExistingBlankSpace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankSpace.setId(longCount.incrementAndGet());

        // Create the BlankSpace
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlankSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, blankSpaceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blankSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlankSpace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankSpace.setId(longCount.incrementAndGet());

        // Create the BlankSpace
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blankSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlankSpace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blankSpace.setId(longCount.incrementAndGet());

        // Create the BlankSpace
        BlankSpaceDTO blankSpaceDTO = blankSpaceMapper.toDto(blankSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlankSpaceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(blankSpaceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlankSpace in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBlankSpace() throws Exception {
        // Initialize the database
        insertedBlankSpace = blankSpaceRepository.saveAndFlush(blankSpace);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the blankSpace
        restBlankSpaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, blankSpace.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return blankSpaceRepository.count();
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

    protected BlankSpace getPersistedBlankSpace(BlankSpace blankSpace) {
        return blankSpaceRepository.findById(blankSpace.getId()).orElseThrow();
    }

    protected void assertPersistedBlankSpaceToMatchAllProperties(BlankSpace expectedBlankSpace) {
        assertBlankSpaceAllPropertiesEquals(expectedBlankSpace, getPersistedBlankSpace(expectedBlankSpace));
    }

    protected void assertPersistedBlankSpaceToMatchUpdatableProperties(BlankSpace expectedBlankSpace) {
        assertBlankSpaceAllUpdatablePropertiesEquals(expectedBlankSpace, getPersistedBlankSpace(expectedBlankSpace));
    }
}
