package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.AnswerOptionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.AnswerOption}.
 */
public interface AnswerOptionService {
    /**
     * Save a answerOption.
     *
     * @param answerOptionDTO the entity to save.
     * @return the persisted entity.
     */
    AnswerOptionDTO save(AnswerOptionDTO answerOptionDTO);

    /**
     * Updates a answerOption.
     *
     * @param answerOptionDTO the entity to update.
     * @return the persisted entity.
     */
    AnswerOptionDTO update(AnswerOptionDTO answerOptionDTO);

    /**
     * Partially updates a answerOption.
     *
     * @param answerOptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnswerOptionDTO> partialUpdate(AnswerOptionDTO answerOptionDTO);

    /**
     * Get all the answerOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnswerOptionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" answerOption.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerOptionDTO> findOne(Long id);

    /**
     * Delete the "id" answerOption.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
