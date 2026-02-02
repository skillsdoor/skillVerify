package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.ExamSessionAsserts.*;
import static com.skillsdoor.skillverify.domain.ExamSessionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExamSessionMapperTest {

    private ExamSessionMapper examSessionMapper;

    @BeforeEach
    void setUp() {
        examSessionMapper = new ExamSessionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getExamSessionSample1();
        var actual = examSessionMapper.toEntity(examSessionMapper.toDto(expected));
        assertExamSessionAllPropertiesEquals(expected, actual);
    }
}
