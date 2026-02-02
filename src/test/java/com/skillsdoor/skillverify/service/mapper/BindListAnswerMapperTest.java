package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.BindListAnswerAsserts.*;
import static com.skillsdoor.skillverify.domain.BindListAnswerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BindListAnswerMapperTest {

    private BindListAnswerMapper bindListAnswerMapper;

    @BeforeEach
    void setUp() {
        bindListAnswerMapper = new BindListAnswerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBindListAnswerSample1();
        var actual = bindListAnswerMapper.toEntity(bindListAnswerMapper.toDto(expected));
        assertBindListAnswerAllPropertiesEquals(expected, actual);
    }
}
