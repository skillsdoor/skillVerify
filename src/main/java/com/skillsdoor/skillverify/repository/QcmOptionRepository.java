package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.QcmOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QcmOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QcmOptionRepository extends JpaRepository<QcmOption, Long> {}
