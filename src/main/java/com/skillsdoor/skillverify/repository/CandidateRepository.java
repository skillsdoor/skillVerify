package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.Candidate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Candidate entity.
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    default Optional<Candidate> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Candidate> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Candidate> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select candidate from Candidate candidate left join fetch candidate.user",
        countQuery = "select count(candidate) from Candidate candidate"
    )
    Page<Candidate> findAllWithToOneRelationships(Pageable pageable);

    @Query("select candidate from Candidate candidate left join fetch candidate.user")
    List<Candidate> findAllWithToOneRelationships();

    @Query("select candidate from Candidate candidate left join fetch candidate.user where candidate.id =:id")
    Optional<Candidate> findOneWithToOneRelationships(@Param("id") Long id);
}
