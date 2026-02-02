package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.CandidateAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CandidateAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateAnswerRepository extends JpaRepository<CandidateAnswer, Long> {}
