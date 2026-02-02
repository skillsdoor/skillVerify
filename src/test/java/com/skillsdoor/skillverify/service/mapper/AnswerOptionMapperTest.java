package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.AnswerOptionAsserts.*;
import static com.skillsdoor.skillverify.domain.AnswerOptionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnswerOptionMapperTest {

    private AnswerOptionMapper answerOptionMapper;

    @BeforeEach
    void setUp() {
        answerOptionMapper = new AnswerOptionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAnswerOptionSample1();
        var actual = answerOptionMapper.toEntity(answerOptionMapper.toDto(expected));
        assertAnswerOptionAllPropertiesEquals(expected, actual);
    }
}
