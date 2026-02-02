package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.BindListAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.BindList;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.repository.BindListRepository;
import com.skillsdoor.skillverify.service.dto.BindListDTO;
import com.skillsdoor.skillverify.service.mapper.BindListMapper;
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
 * Integration tests for the {@link BindListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BindListResourceIT {

    private static final String DEFAULT_LEFT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_LEFT_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_RIGHT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_RIGHT_ITEM = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final String ENTITY_API_URL = "/api/bind-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BindListRepository bindListRepository;

    @Autowired
    private BindListMapper bindListMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBindListMockMvc;

    private BindList bindList;

    private BindList insertedBindList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BindList createEntity(EntityManager em) {
        BindList bindList = new BindList().leftItem(DEFAULT_LEFT_ITEM).rightItem(DEFAULT_RIGHT_ITEM).orderIndex(DEFAULT_ORDER_INDEX);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        bindList.setQuestion(question);
        return bindList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BindList createUpdatedEntity(EntityManager em) {
        BindList updatedBindList = new BindList().leftItem(UPDATED_LEFT_ITEM).rightItem(UPDATED_RIGHT_ITEM).orderIndex(UPDATED_ORDER_INDEX);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createUpdatedEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        updatedBindList.setQuestion(question);
        return updatedBindList;
    }

    @BeforeEach
    void initTest() {
        bindList = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedBindList != null) {
            bindListRepository.delete(insertedBindList);
            insertedBindList = null;
        }
    }

    @Test
    @Transactional
    void createBindList() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BindList
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);
        var returnedBindListDTO = om.readValue(
            restBindListMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BindListDTO.class
        );

        // Validate the BindList in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBindList = bindListMapper.toEntity(returnedBindListDTO);
        assertBindListUpdatableFieldsEquals(returnedBindList, getPersistedBindList(returnedBindList));

        insertedBindList = returnedBindList;
    }

    @Test
    @Transactional
    void createBindListWithExistingId() throws Exception {
        // Create the BindList with an existing ID
        bindList.setId(1L);
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBindListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLeftItemIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bindList.setLeftItem(null);

        // Create the BindList, which fails.
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        restBindListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRightItemIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bindList.setRightItem(null);

        // Create the BindList, which fails.
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        restBindListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIndexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bindList.setOrderIndex(null);

        // Create the BindList, which fails.
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        restBindListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBindLists() throws Exception {
        // Initialize the database
        insertedBindList = bindListRepository.saveAndFlush(bindList);

        // Get all the bindListList
        restBindListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bindList.getId().intValue())))
            .andExpect(jsonPath("$.[*].leftItem").value(hasItem(DEFAULT_LEFT_ITEM)))
            .andExpect(jsonPath("$.[*].rightItem").value(hasItem(DEFAULT_RIGHT_ITEM)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)));
    }

    @Test
    @Transactional
    void getBindList() throws Exception {
        // Initialize the database
        insertedBindList = bindListRepository.saveAndFlush(bindList);

        // Get the bindList
        restBindListMockMvc
            .perform(get(ENTITY_API_URL_ID, bindList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bindList.getId().intValue()))
            .andExpect(jsonPath("$.leftItem").value(DEFAULT_LEFT_ITEM))
            .andExpect(jsonPath("$.rightItem").value(DEFAULT_RIGHT_ITEM))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX));
    }

    @Test
    @Transactional
    void getNonExistingBindList() throws Exception {
        // Get the bindList
        restBindListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBindList() throws Exception {
        // Initialize the database
        insertedBindList = bindListRepository.saveAndFlush(bindList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindList
        BindList updatedBindList = bindListRepository.findById(bindList.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBindList are not directly saved in db
        em.detach(updatedBindList);
        updatedBindList.leftItem(UPDATED_LEFT_ITEM).rightItem(UPDATED_RIGHT_ITEM).orderIndex(UPDATED_ORDER_INDEX);
        BindListDTO bindListDTO = bindListMapper.toDto(updatedBindList);

        restBindListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bindListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindListDTO))
            )
            .andExpect(status().isOk());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBindListToMatchAllProperties(updatedBindList);
    }

    @Test
    @Transactional
    void putNonExistingBindList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindList.setId(longCount.incrementAndGet());

        // Create the BindList
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBindListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bindListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBindList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindList.setId(longCount.incrementAndGet());

        // Create the BindList
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bindListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBindList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindList.setId(longCount.incrementAndGet());

        // Create the BindList
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bindListDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBindListWithPatch() throws Exception {
        // Initialize the database
        insertedBindList = bindListRepository.saveAndFlush(bindList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindList using partial update
        BindList partialUpdatedBindList = new BindList();
        partialUpdatedBindList.setId(bindList.getId());

        partialUpdatedBindList.rightItem(UPDATED_RIGHT_ITEM);

        restBindListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBindList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBindList))
            )
            .andExpect(status().isOk());

        // Validate the BindList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBindListUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBindList, bindList), getPersistedBindList(bindList));
    }

    @Test
    @Transactional
    void fullUpdateBindListWithPatch() throws Exception {
        // Initialize the database
        insertedBindList = bindListRepository.saveAndFlush(bindList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bindList using partial update
        BindList partialUpdatedBindList = new BindList();
        partialUpdatedBindList.setId(bindList.getId());

        partialUpdatedBindList.leftItem(UPDATED_LEFT_ITEM).rightItem(UPDATED_RIGHT_ITEM).orderIndex(UPDATED_ORDER_INDEX);

        restBindListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBindList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBindList))
            )
            .andExpect(status().isOk());

        // Validate the BindList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBindListUpdatableFieldsEquals(partialUpdatedBindList, getPersistedBindList(partialUpdatedBindList));
    }

    @Test
    @Transactional
    void patchNonExistingBindList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindList.setId(longCount.incrementAndGet());

        // Create the BindList
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBindListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bindListDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bindListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBindList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindList.setId(longCount.incrementAndGet());

        // Create the BindList
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bindListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBindList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bindList.setId(longCount.incrementAndGet());

        // Create the BindList
        BindListDTO bindListDTO = bindListMapper.toDto(bindList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBindListMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bindListDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BindList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBindList() throws Exception {
        // Initialize the database
        insertedBindList = bindListRepository.saveAndFlush(bindList);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bindList
        restBindListMockMvc
            .perform(delete(ENTITY_API_URL_ID, bindList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bindListRepository.count();
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

    protected BindList getPersistedBindList(BindList bindList) {
        return bindListRepository.findById(bindList.getId()).orElseThrow();
    }

    protected void assertPersistedBindListToMatchAllProperties(BindList expectedBindList) {
        assertBindListAllPropertiesEquals(expectedBindList, getPersistedBindList(expectedBindList));
    }

    protected void assertPersistedBindListToMatchUpdatableProperties(BindList expectedBindList) {
        assertBindListAllUpdatablePropertiesEquals(expectedBindList, getPersistedBindList(expectedBindList));
    }
}
