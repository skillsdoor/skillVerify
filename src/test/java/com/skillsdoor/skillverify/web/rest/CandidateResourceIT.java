package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.CandidateAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.Candidate;
import com.skillsdoor.skillverify.domain.User;
import com.skillsdoor.skillverify.repository.CandidateRepository;
import com.skillsdoor.skillverify.repository.UserRepository;
import com.skillsdoor.skillverify.service.CandidateService;
import com.skillsdoor.skillverify.service.dto.CandidateDTO;
import com.skillsdoor.skillverify.service.mapper.CandidateMapper;
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
 * Integration tests for the {@link CandidateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CandidateResourceIT {

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

    private static final String DEFAULT_RESUME_URL = "AAAAAAAAAA";
    private static final String UPDATED_RESUME_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN_URL = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_URL = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEARS_OF_EXPERIENCE = 1;
    private static final Integer UPDATED_YEARS_OF_EXPERIENCE = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/candidates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private CandidateRepository candidateRepositoryMock;

    @Autowired
    private CandidateMapper candidateMapper;

    @Mock
    private CandidateService candidateServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    private Candidate insertedCandidate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .bio(DEFAULT_BIO)
            .avatarUrl(DEFAULT_AVATAR_URL)
            .resumeUrl(DEFAULT_RESUME_URL)
            .linkedinUrl(DEFAULT_LINKEDIN_URL)
            .githubUrl(DEFAULT_GITHUB_URL)
            .yearsOfExperience(DEFAULT_YEARS_OF_EXPERIENCE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        candidate.setUser(user);
        return candidate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createUpdatedEntity(EntityManager em) {
        Candidate updatedCandidate = new Candidate()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .resumeUrl(UPDATED_RESUME_URL)
            .linkedinUrl(UPDATED_LINKEDIN_URL)
            .githubUrl(UPDATED_GITHUB_URL)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedCandidate.setUser(user);
        return updatedCandidate;
    }

    @BeforeEach
    void initTest() {
        candidate = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCandidate != null) {
            candidateRepository.delete(insertedCandidate);
            insertedCandidate = null;
        }
    }

    @Test
    @Transactional
    void createCandidate() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);
        var returnedCandidateDTO = om.readValue(
            restCandidateMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CandidateDTO.class
        );

        // Validate the Candidate in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCandidate = candidateMapper.toEntity(returnedCandidateDTO);
        assertCandidateUpdatableFieldsEquals(returnedCandidate, getPersistedCandidate(returnedCandidate));

        insertedCandidate = returnedCandidate;
    }

    @Test
    @Transactional
    void createCandidateWithExistingId() throws Exception {
        // Create the Candidate with an existing ID
        candidate.setId(1L);
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        candidate.setFirstName(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        candidate.setLastName(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCandidates() throws Exception {
        // Initialize the database
        insertedCandidate = candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].avatarUrl").value(hasItem(DEFAULT_AVATAR_URL)))
            .andExpect(jsonPath("$.[*].resumeUrl").value(hasItem(DEFAULT_RESUME_URL)))
            .andExpect(jsonPath("$.[*].linkedinUrl").value(hasItem(DEFAULT_LINKEDIN_URL)))
            .andExpect(jsonPath("$.[*].githubUrl").value(hasItem(DEFAULT_GITHUB_URL)))
            .andExpect(jsonPath("$.[*].yearsOfExperience").value(hasItem(DEFAULT_YEARS_OF_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(candidateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(candidateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(candidateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(candidateRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCandidate() throws Exception {
        // Initialize the database
        insertedCandidate = candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL_ID, candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.avatarUrl").value(DEFAULT_AVATAR_URL))
            .andExpect(jsonPath("$.resumeUrl").value(DEFAULT_RESUME_URL))
            .andExpect(jsonPath("$.linkedinUrl").value(DEFAULT_LINKEDIN_URL))
            .andExpect(jsonPath("$.githubUrl").value(DEFAULT_GITHUB_URL))
            .andExpect(jsonPath("$.yearsOfExperience").value(DEFAULT_YEARS_OF_EXPERIENCE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidate() throws Exception {
        // Initialize the database
        insertedCandidate = candidateRepository.saveAndFlush(candidate);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findById(candidate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCandidate are not directly saved in db
        em.detach(updatedCandidate);
        updatedCandidate
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .resumeUrl(UPDATED_RESUME_URL)
            .linkedinUrl(UPDATED_LINKEDIN_URL)
            .githubUrl(UPDATED_GITHUB_URL)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CandidateDTO candidateDTO = candidateMapper.toDto(updatedCandidate);

        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCandidateToMatchAllProperties(updatedCandidate);
    }

    @Test
    @Transactional
    void putNonExistingCandidate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        insertedCandidate = candidateRepository.saveAndFlush(candidate);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .createdAt(UPDATED_CREATED_AT);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidateUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCandidate, candidate),
            getPersistedCandidate(candidate)
        );
    }

    @Test
    @Transactional
    void fullUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        insertedCandidate = candidateRepository.saveAndFlush(candidate);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .avatarUrl(UPDATED_AVATAR_URL)
            .resumeUrl(UPDATED_RESUME_URL)
            .linkedinUrl(UPDATED_LINKEDIN_URL)
            .githubUrl(UPDATED_GITHUB_URL)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidateUpdatableFieldsEquals(partialUpdatedCandidate, getPersistedCandidate(partialUpdatedCandidate));
    }

    @Test
    @Transactional
    void patchNonExistingCandidate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(candidateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidate() throws Exception {
        // Initialize the database
        insertedCandidate = candidateRepository.saveAndFlush(candidate);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the candidate
        restCandidateMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return candidateRepository.count();
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

    protected Candidate getPersistedCandidate(Candidate candidate) {
        return candidateRepository.findById(candidate.getId()).orElseThrow();
    }

    protected void assertPersistedCandidateToMatchAllProperties(Candidate expectedCandidate) {
        assertCandidateAllPropertiesEquals(expectedCandidate, getPersistedCandidate(expectedCandidate));
    }

    protected void assertPersistedCandidateToMatchUpdatableProperties(Candidate expectedCandidate) {
        assertCandidateAllUpdatablePropertiesEquals(expectedCandidate, getPersistedCandidate(expectedCandidate));
    }
}
