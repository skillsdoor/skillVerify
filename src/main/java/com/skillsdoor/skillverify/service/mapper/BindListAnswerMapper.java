package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.BindListAnswer;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.service.dto.BindListAnswerDTO;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BindListAnswer} and its DTO {@link BindListAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BindListAnswerMapper extends EntityMapper<BindListAnswerDTO, BindListAnswer> {
    @Mapping(target = "candidateAnswer", source = "candidateAnswer", qualifiedByName = "candidateAnswerId")
    BindListAnswerDTO toDto(BindListAnswer s);

    @Named("candidateAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateAnswerDTO toDtoCandidateAnswerId(CandidateAnswer candidateAnswer);
}
