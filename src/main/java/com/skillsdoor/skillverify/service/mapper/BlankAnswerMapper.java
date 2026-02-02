package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.BlankAnswer;
import com.skillsdoor.skillverify.domain.BlankSpace;
import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.service.dto.BlankAnswerDTO;
import com.skillsdoor.skillverify.service.dto.BlankSpaceDTO;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlankAnswer} and its DTO {@link BlankAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BlankAnswerMapper extends EntityMapper<BlankAnswerDTO, BlankAnswer> {
    @Mapping(target = "candidateAnswer", source = "candidateAnswer", qualifiedByName = "candidateAnswerId")
    @Mapping(target = "blankSpace", source = "blankSpace", qualifiedByName = "blankSpaceId")
    BlankAnswerDTO toDto(BlankAnswer s);

    @Named("candidateAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateAnswerDTO toDtoCandidateAnswerId(CandidateAnswer candidateAnswer);

    @Named("blankSpaceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BlankSpaceDTO toDtoBlankSpaceId(BlankSpace blankSpace);
}
