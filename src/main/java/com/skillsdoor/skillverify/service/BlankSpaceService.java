package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.BlankSpaceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.BlankSpace}.
 */
public interface BlankSpaceService {
    /**
     * Save a blankSpace.
     *
     * @param blankSpaceDTO the entity to save.
     * @return the persisted entity.
     */
    BlankSpaceDTO save(BlankSpaceDTO blankSpaceDTO);

    /**
     * Updates a blankSpace.
     *
     * @param blankSpaceDTO the entity to update.
     * @return the persisted entity.
     */
    BlankSpaceDTO update(BlankSpaceDTO blankSpaceDTO);

    /**
     * Partially updates a blankSpace.
     *
     * @param blankSpaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlankSpaceDTO> partialUpdate(BlankSpaceDTO blankSpaceDTO);

    /**
     * Get all the blankSpaces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlankSpaceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" blankSpace.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlankSpaceDTO> findOne(Long id);

    /**
     * Delete the "id" blankSpace.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
