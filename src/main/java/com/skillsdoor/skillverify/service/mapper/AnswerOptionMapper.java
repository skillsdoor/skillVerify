package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.AnswerOption;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.service.dto.AnswerOptionDTO;
import com.skillsdoor.skillverify.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnswerOption} and its DTO {@link AnswerOptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnswerOptionMapper extends EntityMapper<AnswerOptionDTO, AnswerOption> {
    @Mapping(target = "question", source = "question", qualifiedByName = "questionId")
    AnswerOptionDTO toDto(AnswerOption s);

    @Named("questionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionDTO toDtoQuestionId(Question question);
}
