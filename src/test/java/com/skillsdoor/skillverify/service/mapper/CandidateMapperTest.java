package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.CandidateAsserts.*;
import static com.skillsdoor.skillverify.domain.CandidateTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CandidateMapperTest {

    private CandidateMapper candidateMapper;

    @BeforeEach
    void setUp() {
        candidateMapper = new CandidateMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCandidateSample1();
        var actual = candidateMapper.toEntity(candidateMapper.toDto(expected));
        assertCandidateAllPropertiesEquals(expected, actual);
    }
}
