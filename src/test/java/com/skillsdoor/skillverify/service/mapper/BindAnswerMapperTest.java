package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.BindAnswerAsserts.*;
import static com.skillsdoor.skillverify.domain.BindAnswerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BindAnswerMapperTest {

    private BindAnswerMapper bindAnswerMapper;

    @BeforeEach
    void setUp() {
        bindAnswerMapper = new BindAnswerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBindAnswerSample1();
        var actual = bindAnswerMapper.toEntity(bindAnswerMapper.toDto(expected));
        assertBindAnswerAllPropertiesEquals(expected, actual);
    }
}
