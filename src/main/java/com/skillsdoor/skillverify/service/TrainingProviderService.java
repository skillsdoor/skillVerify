package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.TrainingProviderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.TrainingProvider}.
 */
public interface TrainingProviderService {
    /**
     * Save a trainingProvider.
     *
     * @param trainingProviderDTO the entity to save.
     * @return the persisted entity.
     */
    TrainingProviderDTO save(TrainingProviderDTO trainingProviderDTO);

    /**
     * Updates a trainingProvider.
     *
     * @param trainingProviderDTO the entity to update.
     * @return the persisted entity.
     */
    TrainingProviderDTO update(TrainingProviderDTO trainingProviderDTO);

    /**
     * Partially updates a trainingProvider.
     *
     * @param trainingProviderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainingProviderDTO> partialUpdate(TrainingProviderDTO trainingProviderDTO);

    /**
     * Get all the trainingProviders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingProviderDTO> findAll(Pageable pageable);

    /**
     * Get all the trainingProviders with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingProviderDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" trainingProvider.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainingProviderDTO> findOne(Long id);

    /**
     * Delete the "id" trainingProvider.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
