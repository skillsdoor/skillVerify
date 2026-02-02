package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.QcmAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QcmAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QcmAnswerRepository extends JpaRepository<QcmAnswer, Long> {}
