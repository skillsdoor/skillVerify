package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.BindListAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BindListAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BindListAnswerRepository extends JpaRepository<BindListAnswer, Long> {}
