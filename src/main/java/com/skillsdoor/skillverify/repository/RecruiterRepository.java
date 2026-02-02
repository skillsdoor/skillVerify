package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.Recruiter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recruiter entity.
 */
@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    default Optional<Recruiter> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Recruiter> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Recruiter> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select recruiter from Recruiter recruiter left join fetch recruiter.user",
        countQuery = "select count(recruiter) from Recruiter recruiter"
    )
    Page<Recruiter> findAllWithToOneRelationships(Pageable pageable);

    @Query("select recruiter from Recruiter recruiter left join fetch recruiter.user")
    List<Recruiter> findAllWithToOneRelationships();

    @Query("select recruiter from Recruiter recruiter left join fetch recruiter.user where recruiter.id =:id")
    Optional<Recruiter> findOneWithToOneRelationships(@Param("id") Long id);
}
