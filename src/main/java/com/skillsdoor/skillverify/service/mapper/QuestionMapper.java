package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import com.skillsdoor.skillverify.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {
    @Mapping(target = "exercise", source = "exercise", qualifiedByName = "exerciseId")
    QuestionDTO toDto(Question s);

    @Named("exerciseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExerciseDTO toDtoExerciseId(Exercise exercise);
}
