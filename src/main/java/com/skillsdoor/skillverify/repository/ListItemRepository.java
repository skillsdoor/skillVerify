package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.ListItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ListItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListItemRepository extends JpaRepository<ListItem, Long> {}
