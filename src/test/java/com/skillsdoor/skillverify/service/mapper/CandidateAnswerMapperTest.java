package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.CandidateAnswerAsserts.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CandidateAnswerMapperTest {

    private CandidateAnswerMapper candidateAnswerMapper;

    @BeforeEach
    void setUp() {
        candidateAnswerMapper = new CandidateAnswerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCandidateAnswerSample1();
        var actual = candidateAnswerMapper.toEntity(candidateAnswerMapper.toDto(expected));
        assertCandidateAnswerAllPropertiesEquals(expected, actual);
    }
}
