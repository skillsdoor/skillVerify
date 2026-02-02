package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Media;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.service.dto.MediaDTO;
import com.skillsdoor.skillverify.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Media} and its DTO {@link MediaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {
    @Mapping(target = "question", source = "question", qualifiedByName = "questionId")
    MediaDTO toDto(Media s);

    @Named("questionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionDTO toDtoQuestionId(Question question);
}
