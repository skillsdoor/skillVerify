package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Exam;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.service.dto.ExamDTO;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exercise} and its DTO {@link ExerciseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExerciseMapper extends EntityMapper<ExerciseDTO, Exercise> {
    @Mapping(target = "exam", source = "exam", qualifiedByName = "examId")
    ExerciseDTO toDto(Exercise s);

    @Named("examId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamDTO toDtoExamId(Exam exam);
}
