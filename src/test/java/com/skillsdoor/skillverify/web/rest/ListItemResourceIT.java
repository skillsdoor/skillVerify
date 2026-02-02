package com.skillsdoor.skillverify.web.rest;

import static com.skillsdoor.skillverify.domain.ListItemAsserts.*;
import static com.skillsdoor.skillverify.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsdoor.skillverify.IntegrationTest;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.ListItem;
import com.skillsdoor.skillverify.repository.ListItemRepository;
import com.skillsdoor.skillverify.service.dto.ListItemDTO;
import com.skillsdoor.skillverify.service.mapper.ListItemMapper;
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
 * Integration tests for the {@link ListItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListItemResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_MATCHING_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_MATCHING_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_LEFT_SIDE = false;
    private static final Boolean UPDATED_IS_LEFT_SIDE = true;

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final String ENTITY_API_URL = "/api/list-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private ListItemMapper listItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListItemMockMvc;

    private ListItem listItem;

    private ListItem insertedListItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListItem createEntity(EntityManager em) {
        ListItem listItem = new ListItem()
            .content(DEFAULT_CONTENT)
            .matchingContent(DEFAULT_MATCHING_CONTENT)
            .isLeftSide(DEFAULT_IS_LEFT_SIDE)
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
        listItem.setExercise(exercise);
        return listItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListItem createUpdatedEntity(EntityManager em) {
        ListItem updatedListItem = new ListItem()
            .content(UPDATED_CONTENT)
            .matchingContent(UPDATED_MATCHING_CONTENT)
            .isLeftSide(UPDATED_IS_LEFT_SIDE)
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
        updatedListItem.setExercise(exercise);
        return updatedListItem;
    }

    @BeforeEach
    void initTest() {
        listItem = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedListItem != null) {
            listItemRepository.delete(insertedListItem);
            insertedListItem = null;
        }
    }

    @Test
    @Transactional
    void createListItem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ListItem
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);
        var returnedListItemDTO = om.readValue(
            restListItemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(listItemDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ListItemDTO.class
        );

        // Validate the ListItem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedListItem = listItemMapper.toEntity(returnedListItemDTO);
        assertListItemUpdatableFieldsEquals(returnedListItem, getPersistedListItem(returnedListItem));

        insertedListItem = returnedListItem;
    }

    @Test
    @Transactional
    void createListItemWithExistingId() throws Exception {
        // Create the ListItem with an existing ID
        listItem.setId(1L);
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(listItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        listItem.setContent(null);

        // Create the ListItem, which fails.
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        restListItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(listItemDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatchingContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        listItem.setMatchingContent(null);

        // Create the ListItem, which fails.
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        restListItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(listItemDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsLeftSideIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        listItem.setIsLeftSide(null);

        // Create the ListItem, which fails.
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        restListItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(listItemDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIndexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        listItem.setOrderIndex(null);

        // Create the ListItem, which fails.
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        restListItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(listItemDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllListItems() throws Exception {
        // Initialize the database
        insertedListItem = listItemRepository.saveAndFlush(listItem);

        // Get all the listItemList
        restListItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].matchingContent").value(hasItem(DEFAULT_MATCHING_CONTENT)))
            .andExpect(jsonPath("$.[*].isLeftSide").value(hasItem(DEFAULT_IS_LEFT_SIDE)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)));
    }

    @Test
    @Transactional
    void getListItem() throws Exception {
        // Initialize the database
        insertedListItem = listItemRepository.saveAndFlush(listItem);

        // Get the listItem
        restListItemMockMvc
            .perform(get(ENTITY_API_URL_ID, listItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listItem.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.matchingContent").value(DEFAULT_MATCHING_CONTENT))
            .andExpect(jsonPath("$.isLeftSide").value(DEFAULT_IS_LEFT_SIDE))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX));
    }

    @Test
    @Transactional
    void getNonExistingListItem() throws Exception {
        // Get the listItem
        restListItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingListItem() throws Exception {
        // Initialize the database
        insertedListItem = listItemRepository.saveAndFlush(listItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the listItem
        ListItem updatedListItem = listItemRepository.findById(listItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedListItem are not directly saved in db
        em.detach(updatedListItem);
        updatedListItem
            .content(UPDATED_CONTENT)
            .matchingContent(UPDATED_MATCHING_CONTENT)
            .isLeftSide(UPDATED_IS_LEFT_SIDE)
            .orderIndex(UPDATED_ORDER_INDEX);
        ListItemDTO listItemDTO = listItemMapper.toDto(updatedListItem);

        restListItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(listItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedListItemToMatchAllProperties(updatedListItem);
    }

    @Test
    @Transactional
    void putNonExistingListItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        listItem.setId(longCount.incrementAndGet());

        // Create the ListItem
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(listItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        listItem.setId(longCount.incrementAndGet());

        // Create the ListItem
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(listItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        listItem.setId(longCount.incrementAndGet());

        // Create the ListItem
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(listItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListItemWithPatch() throws Exception {
        // Initialize the database
        insertedListItem = listItemRepository.saveAndFlush(listItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the listItem using partial update
        ListItem partialUpdatedListItem = new ListItem();
        partialUpdatedListItem.setId(listItem.getId());

        partialUpdatedListItem.content(UPDATED_CONTENT).matchingContent(UPDATED_MATCHING_CONTENT).isLeftSide(UPDATED_IS_LEFT_SIDE);

        restListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedListItem))
            )
            .andExpect(status().isOk());

        // Validate the ListItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertListItemUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedListItem, listItem), getPersistedListItem(listItem));
    }

    @Test
    @Transactional
    void fullUpdateListItemWithPatch() throws Exception {
        // Initialize the database
        insertedListItem = listItemRepository.saveAndFlush(listItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the listItem using partial update
        ListItem partialUpdatedListItem = new ListItem();
        partialUpdatedListItem.setId(listItem.getId());

        partialUpdatedListItem
            .content(UPDATED_CONTENT)
            .matchingContent(UPDATED_MATCHING_CONTENT)
            .isLeftSide(UPDATED_IS_LEFT_SIDE)
            .orderIndex(UPDATED_ORDER_INDEX);

        restListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedListItem))
            )
            .andExpect(status().isOk());

        // Validate the ListItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertListItemUpdatableFieldsEquals(partialUpdatedListItem, getPersistedListItem(partialUpdatedListItem));
    }

    @Test
    @Transactional
    void patchNonExistingListItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        listItem.setId(longCount.incrementAndGet());

        // Create the ListItem
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(listItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        listItem.setId(longCount.incrementAndGet());

        // Create the ListItem
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(listItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        listItem.setId(longCount.incrementAndGet());

        // Create the ListItem
        ListItemDTO listItemDTO = listItemMapper.toDto(listItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(listItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListItem() throws Exception {
        // Initialize the database
        insertedListItem = listItemRepository.saveAndFlush(listItem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the listItem
        restListItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, listItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return listItemRepository.count();
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

    protected ListItem getPersistedListItem(ListItem listItem) {
        return listItemRepository.findById(listItem.getId()).orElseThrow();
    }

    protected void assertPersistedListItemToMatchAllProperties(ListItem expectedListItem) {
        assertListItemAllPropertiesEquals(expectedListItem, getPersistedListItem(expectedListItem));
    }

    protected void assertPersistedListItemToMatchUpdatableProperties(ListItem expectedListItem) {
        assertListItemAllUpdatablePropertiesEquals(expectedListItem, getPersistedListItem(expectedListItem));
    }
}
