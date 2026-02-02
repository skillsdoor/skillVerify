package com.skillsdoor.skillverify.service;

import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skillsdoor.skillverify.domain.CandidateAnswer}.
 */
public interface CandidateAnswerService {
    /**
     * Save a candidateAnswer.
     *
     * @param candidateAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    CandidateAnswerDTO save(CandidateAnswerDTO candidateAnswerDTO);

    /**
     * Updates a candidateAnswer.
     *
     * @param candidateAnswerDTO the entity to update.
     * @return the persisted entity.
     */
    CandidateAnswerDTO update(CandidateAnswerDTO candidateAnswerDTO);

    /**
     * Partially updates a candidateAnswer.
     *
     * @param candidateAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CandidateAnswerDTO> partialUpdate(CandidateAnswerDTO candidateAnswerDTO);

    /**
     * Get all the candidateAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidateAnswerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" candidateAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidateAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" candidateAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
