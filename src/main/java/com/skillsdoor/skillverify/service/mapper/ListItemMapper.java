package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.ListItem;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import com.skillsdoor.skillverify.service.dto.ListItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListItem} and its DTO {@link ListItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ListItemMapper extends EntityMapper<ListItemDTO, ListItem> {
    @Mapping(target = "exercise", source = "exercise", qualifiedByName = "exerciseId")
    ListItemDTO toDto(ListItem s);

    @Named("exerciseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExerciseDTO toDtoExerciseId(Exercise exercise);
}
