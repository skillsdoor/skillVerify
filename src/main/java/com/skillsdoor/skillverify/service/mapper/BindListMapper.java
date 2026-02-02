package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.BindList;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.service.dto.BindListDTO;
import com.skillsdoor.skillverify.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BindList} and its DTO {@link BindListDTO}.
 */
@Mapper(componentModel = "spring")
public interface BindListMapper extends EntityMapper<BindListDTO, BindList> {
    @Mapping(target = "question", source = "question", qualifiedByName = "questionId")
    BindListDTO toDto(BindList s);

    @Named("questionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionDTO toDtoQuestionId(Question question);
}
