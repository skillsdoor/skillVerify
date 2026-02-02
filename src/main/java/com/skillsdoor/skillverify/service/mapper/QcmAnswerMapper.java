package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.domain.QcmAnswer;
import com.skillsdoor.skillverify.domain.QcmOption;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import com.skillsdoor.skillverify.service.dto.QcmAnswerDTO;
import com.skillsdoor.skillverify.service.dto.QcmOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QcmAnswer} and its DTO {@link QcmAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface QcmAnswerMapper extends EntityMapper<QcmAnswerDTO, QcmAnswer> {
    @Mapping(target = "candidateAnswer", source = "candidateAnswer", qualifiedByName = "candidateAnswerId")
    @Mapping(target = "qcmOption", source = "qcmOption", qualifiedByName = "qcmOptionId")
    QcmAnswerDTO toDto(QcmAnswer s);

    @Named("candidateAnswerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateAnswerDTO toDtoCandidateAnswerId(CandidateAnswer candidateAnswer);

    @Named("qcmOptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QcmOptionDTO toDtoQcmOptionId(QcmOption qcmOption);
}
