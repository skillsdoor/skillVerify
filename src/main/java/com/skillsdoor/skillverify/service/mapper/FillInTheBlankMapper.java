package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.FillInTheBlank;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.service.dto.FillInTheBlankDTO;
import com.skillsdoor.skillverify.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FillInTheBlank} and its DTO {@link FillInTheBlankDTO}.
 */
@Mapper(componentModel = "spring")
public interface FillInTheBlankMapper extends EntityMapper<FillInTheBlankDTO, FillInTheBlank> {
    @Mapping(target = "question", source = "question", qualifiedByName = "questionId")
    FillInTheBlankDTO toDto(FillInTheBlank s);

    @Named("questionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionDTO toDtoQuestionId(Question question);
}
