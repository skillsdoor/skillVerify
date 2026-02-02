package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.QcmOption;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import com.skillsdoor.skillverify.service.dto.QcmOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QcmOption} and its DTO {@link QcmOptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface QcmOptionMapper extends EntityMapper<QcmOptionDTO, QcmOption> {
    @Mapping(target = "exercise", source = "exercise", qualifiedByName = "exerciseId")
    QcmOptionDTO toDto(QcmOption s);

    @Named("exerciseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExerciseDTO toDtoExerciseId(Exercise exercise);
}
