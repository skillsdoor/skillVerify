package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.TrainingProviderAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.TrainingProvider;
import com.skillsdoor.skillverify.domain.User;
import com.skillsdoor.skillverify.repository.TrainingProviderRepository;
import com.skillsdoor.skillverify.repository.UserRepository;
import com.skillsdoor.skillverify.service.TrainingProviderService;
import com.skillsdoor.skillverify.service.dto.TrainingProviderDTO;
import com.skillsdoor.skillverify.service.mapper.TrainingProviderMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrainingProviderResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TrainingProviderResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR_URL = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/training-providers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrainingProviderRepository trainingProviderRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private TrainingProviderRepository trainingProviderRepositoryMock;

    @Autowired
    private TrainingProviderMapper trainingProviderMapper;

    @Mock
    private TrainingProviderService trainingProviderServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingProviderMockMvc;

    private TrainingProvider trainingProvider;

    private TrainingProvider insertedTrainingProvider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingProvider createEntity(EntityManager em) {
        TrainingProvider trainingProvider = new TrainingProvider()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .bio(DEFAULT_BIO)
            .avatarUrl(DEFAULT_AVATAR_URL)
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .website(DEFAULT_WEBSITE)
            .accreditation(DEFAULT_ACCREDITATION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        trainingProvider.setUser(user);
        return trainingProvider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingProvider createUpdatedEntity(EntityManager em) {
        TrainingProvider updatedTrainingProvider = new TrainingProvider()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .website(UPDATED_WEBSITE)
            .accreditation(UPDATED_ACCREDITATION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedTrainingProvider.setUser(user);
        return updatedTrainingProvider;
    }

    @BeforeEach
    void initTest() {
        trainingProvider = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTrainingProvider != null) {
            trainingProviderRepository.delete(insertedTrainingProvider);
            insertedTrainingProvider = null;
        }
    }

    @Test
    @Transactional
    void createTrainingProvider() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TrainingProvider
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);
        var returnedTrainingProviderDTO = om.readValue(
            restTrainingProviderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProviderDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TrainingProviderDTO.class
        );

        // Validate the TrainingProvider in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTrainingProvider = trainingProviderMapper.toEntity(returnedTrainingProviderDTO);
        assertTrainingProviderUpdatableFieldsEquals(returnedTrainingProvider, getPersistedTrainingProvider(returnedTrainingProvider));

        insertedTrainingProvider = returnedTrainingProvider;
    }

    @Test
    @Transactional
    void createTrainingProviderWithExistingId() throws Exception {
        // Create the TrainingProvider with an existing ID
        trainingProvider.setId(1L);
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingProviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProviderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trainingProvider.setFirstName(null);

        // Create the TrainingProvider, which fails.
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        restTrainingProviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProviderDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trainingProvider.setLastName(null);

        // Create the TrainingProvider, which fails.
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        restTrainingProviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProviderDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrganizationNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trainingProvider.setOrganizationName(null);

        // Create the TrainingProvider, which fails.
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        restTrainingProviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProviderDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrainingProviders() throws Exception {
        // Initialize the database
        insertedTrainingProvider = trainingProviderRepository.saveAndFlush(trainingProvider);

        // Get all the trainingProviderList
        restTrainingProviderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].avatarUrl").value(hasItem(DEFAULT_AVATAR_URL)))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].accreditation").value(hasItem(DEFAULT_ACCREDITATION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTrainingProvidersWithEagerRelationshipsIsEnabled() throws Exception {
        when(trainingProviderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTrainingProviderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(trainingProviderServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTrainingProvidersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(trainingProviderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTrainingProviderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(trainingProviderRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTrainingProvider() throws Exception {
        // Initialize the database
        insertedTrainingProvider = trainingProviderRepository.saveAndFlush(trainingProvider);

        // Get the trainingProvider
        restTrainingProviderMockMvc
            .perform(get(ENTITY_API_URL_ID, trainingProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingProvider.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.avatarUrl").value(DEFAULT_AVATAR_URL))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.accreditation").value(DEFAULT_ACCREDITATION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrainingProvider() throws Exception {
        // Get the trainingProvider
        restTrainingProviderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainingProvider() throws Exception {
        // Initialize the database
        insertedTrainingProvider = trainingProviderRepository.saveAndFlush(trainingProvider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trainingProvider
        TrainingProvider updatedTrainingProvider = trainingProviderRepository.findById(trainingProvider.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTrainingProvider are not directly saved in db
        em.detach(updatedTrainingProvider);
        updatedTrainingProvider
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .website(UPDATED_WEBSITE)
            .accreditation(UPDATED_ACCREDITATION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(updatedTrainingProvider);

        restTrainingProviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingProviderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trainingProviderDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrainingProviderToMatchAllProperties(updatedTrainingProvider);
    }

    @Test
    @Transactional
    void putNonExistingTrainingProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProvider.setId(longCount.incrementAndGet());

        // Create the TrainingProvider
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingProviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingProviderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trainingProviderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainingProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProvider.setId(longCount.incrementAndGet());

        // Create the TrainingProvider
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trainingProviderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainingProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProvider.setId(longCount.incrementAndGet());

        // Create the TrainingProvider
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProviderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProviderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingProviderWithPatch() throws Exception {
        // Initialize the database
        insertedTrainingProvider = trainingProviderRepository.saveAndFlush(trainingProvider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trainingProvider using partial update
        TrainingProvider partialUpdatedTrainingProvider = new TrainingProvider();
        partialUpdatedTrainingProvider.setId(trainingProvider.getId());

        partialUpdatedTrainingProvider
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .website(UPDATED_WEBSITE)
            .accreditation(UPDATED_ACCREDITATION)
            .createdAt(UPDATED_CREATED_AT);

        restTrainingProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingProvider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrainingProvider))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProvider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrainingProviderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrainingProvider, trainingProvider),
            getPersistedTrainingProvider(trainingProvider)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrainingProviderWithPatch() throws Exception {
        // Initialize the database
        insertedTrainingProvider = trainingProviderRepository.saveAndFlush(trainingProvider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trainingProvider using partial update
        TrainingProvider partialUpdatedTrainingProvider = new TrainingProvider();
        partialUpdatedTrainingProvider.setId(trainingProvider.getId());

        partialUpdatedTrainingProvider
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .website(UPDATED_WEBSITE)
            .accreditation(UPDATED_ACCREDITATION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTrainingProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingProvider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrainingProvider))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProvider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrainingProviderUpdatableFieldsEquals(
            partialUpdatedTrainingProvider,
            getPersistedTrainingProvider(partialUpdatedTrainingProvider)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTrainingProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProvider.setId(longCount.incrementAndGet());

        // Create the TrainingProvider
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainingProviderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trainingProviderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainingProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProvider.setId(longCount.incrementAndGet());

        // Create the TrainingProvider
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trainingProviderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainingProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProvider.setId(longCount.incrementAndGet());

        // Create the TrainingProvider
        TrainingProviderDTO trainingProviderDTO = trainingProviderMapper.toDto(trainingProvider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProviderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trainingProviderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainingProvider() throws Exception {
        // Initialize the database
        insertedTrainingProvider = trainingProviderRepository.saveAndFlush(trainingProvider);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trainingProvider
        restTrainingProviderMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainingProvider.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trainingProviderRepository.count();
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

    protected TrainingProvider getPersistedTrainingProvider(TrainingProvider trainingProvider) {
        return trainingProviderRepository.findById(trainingProvider.getId()).orElseThrow();
    }

    protected void assertPersistedTrainingProviderToMatchAllProperties(TrainingProvider expectedTrainingProvider) {
        assertTrainingProviderAllPropertiesEquals(expectedTrainingProvider, getPersistedTrainingProvider(expectedTrainingProvider));
    }

    protected void assertPersistedTrainingProviderToMatchUpdatableProperties(TrainingProvider expectedTrainingProvider) {
        assertTrainingProviderAllUpdatablePropertiesEquals(
            expectedTrainingProvider,
            getPersistedTrainingProvider(expectedTrainingProvider)
        );
    }
}
