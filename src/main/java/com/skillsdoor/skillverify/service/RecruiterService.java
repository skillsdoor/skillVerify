package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.RecruiterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.Recruiter}.
 */
public interface RecruiterService {
    /**
     * Save a recruiter.
     *
     * @param recruiterDTO the entity to save.
     * @return the persisted entity.
     */
    RecruiterDTO save(RecruiterDTO recruiterDTO);

    /**
     * Updates a recruiter.
     *
     * @param recruiterDTO the entity to update.
     * @return the persisted entity.
     */
    RecruiterDTO update(RecruiterDTO recruiterDTO);

    /**
     * Partially updates a recruiter.
     *
     * @param recruiterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecruiterDTO> partialUpdate(RecruiterDTO recruiterDTO);

    /**
     * Get all the recruiters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecruiterDTO> findAll(Pageable pageable);

    /**
     * Get all the recruiters with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecruiterDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" recruiter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecruiterDTO> findOne(Long id);

    /**
     * Delete the "id" recruiter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
