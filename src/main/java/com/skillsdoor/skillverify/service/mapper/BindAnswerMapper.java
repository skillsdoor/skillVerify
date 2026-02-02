package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.BindAnswer;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.service.dto.BindAnswerDTO;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BindAnswer} and its DTO {@link BindAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BindAnswerMapper extends EntityMapper<BindAnswerDTO, BindAnswer> {
    @Mapping(target = "candidateAnswer", source = "candidateAnswer", qualifiedByName = "candidateAnswerId")
    BindAnswerDTO toDto(BindAnswer s);

    @Named("candidateAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateAnswerDTO toDtoCandidateAnswerId(CandidateAnswer candidateAnswer);
}
