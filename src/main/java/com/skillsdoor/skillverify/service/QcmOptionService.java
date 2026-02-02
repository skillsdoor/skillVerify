package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.QcmOptionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.QcmOption}.
 */
public interface QcmOptionService {
    /**
     * Save a qcmOption.
     *
     * @param qcmOptionDTO the entity to save.
     * @return the persisted entity.
     */
    QcmOptionDTO save(QcmOptionDTO qcmOptionDTO);

    /**
     * Updates a qcmOption.
     *
     * @param qcmOptionDTO the entity to update.
     * @return the persisted entity.
     */
    QcmOptionDTO update(QcmOptionDTO qcmOptionDTO);

    /**
     * Partially updates a qcmOption.
     *
     * @param qcmOptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QcmOptionDTO> partialUpdate(QcmOptionDTO qcmOptionDTO);

    /**
     * Get all the qcmOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QcmOptionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" qcmOption.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QcmOptionDTO> findOne(Long id);

    /**
     * Delete the "id" qcmOption.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
