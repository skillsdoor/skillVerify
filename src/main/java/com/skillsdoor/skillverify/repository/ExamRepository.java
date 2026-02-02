package com.skillsdoor.skillverify.repository;

import com.skillsdoor.skillverify.domain.Exam;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Exam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("SELECT e FROM Exam e LEFT JOIN FETCH e.exercises WHERE e.id = :id")
    Optional<Exam> findByIdWithExercises(@Param("id") Long id);
}
