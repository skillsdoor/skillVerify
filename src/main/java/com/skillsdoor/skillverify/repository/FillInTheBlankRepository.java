package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.FillInTheBlank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FillInTheBlank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FillInTheBlankRepository extends JpaRepository<FillInTheBlank, Long> {}
