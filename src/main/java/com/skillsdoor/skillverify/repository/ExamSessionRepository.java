package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.ExamSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ExamSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {}
