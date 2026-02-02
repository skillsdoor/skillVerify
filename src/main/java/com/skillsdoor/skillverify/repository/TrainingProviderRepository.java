package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.TrainingProvider;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TrainingProvider entity.
 */
@Repository
public interface TrainingProviderRepository extends JpaRepository<TrainingProvider, Long> {
    default Optional<TrainingProvider> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TrainingProvider> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TrainingProvider> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select trainingProvider from TrainingProvider trainingProvider left join fetch trainingProvider.user",
        countQuery = "select count(trainingProvider) from TrainingProvider trainingProvider"
    )
    Page<TrainingProvider> findAllWithToOneRelationships(Pageable pageable);

    @Query("select trainingProvider from TrainingProvider trainingProvider left join fetch trainingProvider.user")
    List<TrainingProvider> findAllWithToOneRelationships();

    @Query(
        "select trainingProvider from TrainingProvider trainingProvider left join fetch trainingProvider.user where trainingProvider.id =:id"
    )
    Optional<TrainingProvider> findOneWithToOneRelationships(@Param("id") Long id);
}
