package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.BindList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BindList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BindListRepository extends JpaRepository<BindList, Long> {}
