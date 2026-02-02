package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.QcmAnswerAsserts.*;
import static com.skillsdoor.skillverify.domain.QcmAnswerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QcmAnswerMapperTest {

    private QcmAnswerMapper qcmAnswerMapper;

    @BeforeEach
    void setUp() {
        qcmAnswerMapper = new QcmAnswerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getQcmAnswerSample1();
        var actual = qcmAnswerMapper.toEntity(qcmAnswerMapper.toDto(expected));
        assertQcmAnswerAllPropertiesEquals(expected, actual);
    }
}
