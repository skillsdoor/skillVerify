package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.RecruiterAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.Recruiter;
import com.skillsdoor.skillverify.domain.User;
import com.skillsdoor.skillverify.repository.RecruiterRepository;
import com.skillsdoor.skillverify.repository.UserRepository;
import com.skillsdoor.skillverify.service.RecruiterService;
import com.skillsdoor.skillverify.service.dto.RecruiterDTO;
import com.skillsdoor.skillverify.service.mapper.RecruiterMapper;
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
 * Integration tests for the {@link RecruiterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RecruiterResourceIT {

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

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/recruiters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private RecruiterRepository recruiterRepositoryMock;

    @Autowired
    private RecruiterMapper recruiterMapper;

    @Mock
    private RecruiterService recruiterServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecruiterMockMvc;

    private Recruiter recruiter;

    private Recruiter insertedRecruiter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recruiter createEntity(EntityManager em) {
        Recruiter recruiter = new Recruiter()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .bio(DEFAULT_BIO)
            .avatarUrl(DEFAULT_AVATAR_URL)
            .company(DEFAULT_COMPANY)
            .position(DEFAULT_POSITION)
            .website(DEFAULT_WEBSITE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        recruiter.setUser(user);
        return recruiter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recruiter createUpdatedEntity(EntityManager em) {
        Recruiter updatedRecruiter = new Recruiter()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .company(UPDATED_COMPANY)
            .position(UPDATED_POSITION)
            .website(UPDATED_WEBSITE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedRecruiter.setUser(user);
        return updatedRecruiter;
    }

    @BeforeEach
    void initTest() {
        recruiter = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedRecruiter != null) {
            recruiterRepository.delete(insertedRecruiter);
            insertedRecruiter = null;
        }
    }

    @Test
    @Transactional
    void createRecruiter() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);
        var returnedRecruiterDTO = om.readValue(
            restRecruiterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recruiterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RecruiterDTO.class
        );

        // Validate the Recruiter in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRecruiter = recruiterMapper.toEntity(returnedRecruiterDTO);
        assertRecruiterUpdatableFieldsEquals(returnedRecruiter, getPersistedRecruiter(returnedRecruiter));

        insertedRecruiter = returnedRecruiter;
    }

    @Test
    @Transactional
    void createRecruiterWithExistingId() throws Exception {
        // Create the Recruiter with an existing ID
        recruiter.setId(1L);
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecruiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recruiterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recruiter.setFirstName(null);

        // Create the Recruiter, which fails.
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        restRecruiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recruiterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recruiter.setLastName(null);

        // Create the Recruiter, which fails.
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        restRecruiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recruiterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recruiter.setCompany(null);

        // Create the Recruiter, which fails.
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        restRecruiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recruiterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecruiters() throws Exception {
        // Initialize the database
        insertedRecruiter = recruiterRepository.saveAndFlush(recruiter);

        // Get all the recruiterList
        restRecruiterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recruiter.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].avatarUrl").value(hasItem(DEFAULT_AVATAR_URL)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecruitersWithEagerRelationshipsIsEnabled() throws Exception {
        when(recruiterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecruiterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(recruiterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecruitersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(recruiterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecruiterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(recruiterRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRecruiter() throws Exception {
        // Initialize the database
        insertedRecruiter = recruiterRepository.saveAndFlush(recruiter);

        // Get the recruiter
        restRecruiterMockMvc
            .perform(get(ENTITY_API_URL_ID, recruiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recruiter.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.avatarUrl").value(DEFAULT_AVATAR_URL))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRecruiter() throws Exception {
        // Get the recruiter
        restRecruiterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecruiter() throws Exception {
        // Initialize the database
        insertedRecruiter = recruiterRepository.saveAndFlush(recruiter);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recruiter
        Recruiter updatedRecruiter = recruiterRepository.findById(recruiter.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecruiter are not directly saved in db
        em.detach(updatedRecruiter);
        updatedRecruiter
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .company(UPDATED_COMPANY)
            .position(UPDATED_POSITION)
            .website(UPDATED_WEBSITE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(updatedRecruiter);

        restRecruiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recruiterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recruiterDTO))
            )
            .andExpect(status().isOk());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecruiterToMatchAllProperties(updatedRecruiter);
    }

    @Test
    @Transactional
    void putNonExistingRecruiter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recruiter.setId(longCount.incrementAndGet());

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recruiterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recruiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecruiter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recruiter.setId(longCount.incrementAndGet());

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recruiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecruiter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recruiter.setId(longCount.incrementAndGet());

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruiterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recruiterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecruiterWithPatch() throws Exception {
        // Initialize the database
        insertedRecruiter = recruiterRepository.saveAndFlush(recruiter);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recruiter using partial update
        Recruiter partialUpdatedRecruiter = new Recruiter();
        partialUpdatedRecruiter.setId(recruiter.getId());

        partialUpdatedRecruiter.firstName(UPDATED_FIRST_NAME).phone(UPDATED_PHONE).bio(UPDATED_BIO).company(UPDATED_COMPANY);

        restRecruiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruiter.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecruiter))
            )
            .andExpect(status().isOk());

        // Validate the Recruiter in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecruiterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRecruiter, recruiter),
            getPersistedRecruiter(recruiter)
        );
    }

    @Test
    @Transactional
    void fullUpdateRecruiterWithPatch() throws Exception {
        // Initialize the database
        insertedRecruiter = recruiterRepository.saveAndFlush(recruiter);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recruiter using partial update
        Recruiter partialUpdatedRecruiter = new Recruiter();
        partialUpdatedRecruiter.setId(recruiter.getId());

        partialUpdatedRecruiter
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .company(UPDATED_COMPANY)
            .position(UPDATED_POSITION)
            .website(UPDATED_WEBSITE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restRecruiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruiter.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecruiter))
            )
            .andExpect(status().isOk());

        // Validate the Recruiter in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecruiterUpdatableFieldsEquals(partialUpdatedRecruiter, getPersistedRecruiter(partialUpdatedRecruiter));
    }

    @Test
    @Transactional
    void patchNonExistingRecruiter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recruiter.setId(longCount.incrementAndGet());

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recruiterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recruiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecruiter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recruiter.setId(longCount.incrementAndGet());

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recruiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecruiter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recruiter.setId(longCount.incrementAndGet());

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruiterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recruiterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recruiter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecruiter() throws Exception {
        // Initialize the database
        insertedRecruiter = recruiterRepository.saveAndFlush(recruiter);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recruiter
        restRecruiterMockMvc
            .perform(delete(ENTITY_API_URL_ID, recruiter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recruiterRepository.count();
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

    protected Recruiter getPersistedRecruiter(Recruiter recruiter) {
        return recruiterRepository.findById(recruiter.getId()).orElseThrow();
    }

    protected void assertPersistedRecruiterToMatchAllProperties(Recruiter expectedRecruiter) {
        assertRecruiterAllPropertiesEquals(expectedRecruiter, getPersistedRecruiter(expectedRecruiter));
    }

    protected void assertPersistedRecruiterToMatchUpdatableProperties(Recruiter expectedRecruiter) {
        assertRecruiterAllUpdatablePropertiesEquals(expectedRecruiter, getPersistedRecruiter(expectedRecruiter));
    }
}
