package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.BindAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BindAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BindAnswerRepository extends JpaRepository<BindAnswer, Long> {}
