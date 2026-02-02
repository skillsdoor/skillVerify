package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.ListItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.ListItem}.
 */
public interface ListItemService {
    /**
     * Save a listItem.
     *
     * @param listItemDTO the entity to save.
     * @return the persisted entity.
     */
    ListItemDTO save(ListItemDTO listItemDTO);

    /**
     * Updates a listItem.
     *
     * @param listItemDTO the entity to update.
     * @return the persisted entity.
     */
    ListItemDTO update(ListItemDTO listItemDTO);

    /**
     * Partially updates a listItem.
     *
     * @param listItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ListItemDTO> partialUpdate(ListItemDTO listItemDTO);

    /**
     * Get all the listItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ListItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" listItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ListItemDTO> findOne(Long id);

    /**
     * Delete the "id" listItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
