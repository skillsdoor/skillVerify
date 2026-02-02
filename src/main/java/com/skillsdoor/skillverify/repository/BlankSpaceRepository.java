package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.BlankSpace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BlankSpace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlankSpaceRepository extends JpaRepository<BlankSpace, Long> {}
