package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.BindListAnswerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.BindListAnswer}.
 */
public interface BindListAnswerService {
    /**
     * Save a bindListAnswer.
     *
     * @param bindListAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    BindListAnswerDTO save(BindListAnswerDTO bindListAnswerDTO);

    /**
     * Updates a bindListAnswer.
     *
     * @param bindListAnswerDTO the entity to update.
     * @return the persisted entity.
     */
    BindListAnswerDTO update(BindListAnswerDTO bindListAnswerDTO);

    /**
     * Partially updates a bindListAnswer.
     *
     * @param bindListAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BindListAnswerDTO> partialUpdate(BindListAnswerDTO bindListAnswerDTO);

    /**
     * Get all the bindListAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BindListAnswerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bindListAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BindListAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" bindListAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
