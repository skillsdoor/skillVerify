package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.ExamSessionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.ExamSession}.
 */
public interface ExamSessionService {
    /**
     * Save a examSession.
     *
     * @param examSessionDTO the entity to save.
     * @return the persisted entity.
     */
    ExamSessionDTO save(ExamSessionDTO examSessionDTO);

    /**
     * Updates a examSession.
     *
     * @param examSessionDTO the entity to update.
     * @return the persisted entity.
     */
    ExamSessionDTO update(ExamSessionDTO examSessionDTO);

    /**
     * Partially updates a examSession.
     *
     * @param examSessionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamSessionDTO> partialUpdate(ExamSessionDTO examSessionDTO);

    /**
     * Get all the examSessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamSessionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examSession.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamSessionDTO> findOne(Long id);

    /**
     * Delete the "id" examSession.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
