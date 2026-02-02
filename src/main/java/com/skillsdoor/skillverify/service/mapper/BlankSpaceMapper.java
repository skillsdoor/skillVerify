package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.BlankSpace;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.service.dto.BlankSpaceDTO;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlankSpace} and its DTO {@link BlankSpaceDTO}.
 */
@Mapper(componentModel = "spring")
public interface BlankSpaceMapper extends EntityMapper<BlankSpaceDTO, BlankSpace> {
    @Mapping(target = "exercise", source = "exercise", qualifiedByName = "exerciseId")
    BlankSpaceDTO toDto(BlankSpace s);

    @Named("exerciseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExerciseDTO toDtoExerciseId(Exercise exercise);
}
