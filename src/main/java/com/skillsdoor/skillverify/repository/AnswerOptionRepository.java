package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.AnswerOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnswerOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {}
