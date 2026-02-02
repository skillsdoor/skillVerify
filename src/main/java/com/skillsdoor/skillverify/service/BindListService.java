package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.BindListDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.BindList}.
 */
public interface BindListService {
    /**
     * Save a bindList.
     *
     * @param bindListDTO the entity to save.
     * @return the persisted entity.
     */
    BindListDTO save(BindListDTO bindListDTO);

    /**
     * Updates a bindList.
     *
     * @param bindListDTO the entity to update.
     * @return the persisted entity.
     */
    BindListDTO update(BindListDTO bindListDTO);

    /**
     * Partially updates a bindList.
     *
     * @param bindListDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BindListDTO> partialUpdate(BindListDTO bindListDTO);

    /**
     * Get all the bindLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BindListDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bindList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BindListDTO> findOne(Long id);

    /**
     * Delete the "id" bindList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
