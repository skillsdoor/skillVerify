package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.FillInTheBlankDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.FillInTheBlank}.
 */
public interface FillInTheBlankService {
    /**
     * Save a fillInTheBlank.
     *
     * @param fillInTheBlankDTO the entity to save.
     * @return the persisted entity.
     */
    FillInTheBlankDTO save(FillInTheBlankDTO fillInTheBlankDTO);

    /**
     * Updates a fillInTheBlank.
     *
     * @param fillInTheBlankDTO the entity to update.
     * @return the persisted entity.
     */
    FillInTheBlankDTO update(FillInTheBlankDTO fillInTheBlankDTO);

    /**
     * Partially updates a fillInTheBlank.
     *
     * @param fillInTheBlankDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FillInTheBlankDTO> partialUpdate(FillInTheBlankDTO fillInTheBlankDTO);

    /**
     * Get all the fillInTheBlanks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FillInTheBlankDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fillInTheBlank.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FillInTheBlankDTO> findOne(Long id);

    /**
     * Delete the "id" fillInTheBlank.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
