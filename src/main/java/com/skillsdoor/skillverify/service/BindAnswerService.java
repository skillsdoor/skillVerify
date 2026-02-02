package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.BindAnswerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.BindAnswer}.
 */
public interface BindAnswerService {
    /**
     * Save a bindAnswer.
     *
     * @param bindAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    BindAnswerDTO save(BindAnswerDTO bindAnswerDTO);

    /**
     * Updates a bindAnswer.
     *
     * @param bindAnswerDTO the entity to update.
     * @return the persisted entity.
     */
    BindAnswerDTO update(BindAnswerDTO bindAnswerDTO);

    /**
     * Partially updates a bindAnswer.
     *
     * @param bindAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BindAnswerDTO> partialUpdate(BindAnswerDTO bindAnswerDTO);

    /**
     * Get all the bindAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BindAnswerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bindAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BindAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" bindAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
