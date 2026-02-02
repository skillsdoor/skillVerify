package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.BlankAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BlankAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlankAnswerRepository extends JpaRepository<BlankAnswer, Long> {}
