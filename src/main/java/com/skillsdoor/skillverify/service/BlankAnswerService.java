package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.BlankAnswerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.BlankAnswer}.
 */
public interface BlankAnswerService {
    /**
     * Save a blankAnswer.
     *
     * @param blankAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    BlankAnswerDTO save(BlankAnswerDTO blankAnswerDTO);

    /**
     * Updates a blankAnswer.
     *
     * @param blankAnswerDTO the entity to update.
     * @return the persisted entity.
     */
    BlankAnswerDTO update(BlankAnswerDTO blankAnswerDTO);

    /**
     * Partially updates a blankAnswer.
     *
     * @param blankAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlankAnswerDTO> partialUpdate(BlankAnswerDTO blankAnswerDTO);

    /**
     * Get all the blankAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlankAnswerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" blankAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlankAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" blankAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
