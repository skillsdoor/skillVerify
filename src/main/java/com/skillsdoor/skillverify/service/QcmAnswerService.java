package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.QcmAnswerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.QcmAnswer}.
 */
public interface QcmAnswerService {
    /**
     * Save a qcmAnswer.
     *
     * @param qcmAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    QcmAnswerDTO save(QcmAnswerDTO qcmAnswerDTO);

    /**
     * Updates a qcmAnswer.
     *
     * @param qcmAnswerDTO the entity to update.
     * @return the persisted entity.
     */
    QcmAnswerDTO update(QcmAnswerDTO qcmAnswerDTO);

    /**
     * Partially updates a qcmAnswer.
     *
     * @param qcmAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QcmAnswerDTO> partialUpdate(QcmAnswerDTO qcmAnswerDTO);

    /**
     * Get all the qcmAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QcmAnswerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" qcmAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QcmAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" qcmAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
