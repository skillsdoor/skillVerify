package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.BlankAnswerAsserts.*;
import static com.skillsdoor.skillverify.domain.BlankAnswerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlankAnswerMapperTest {

    private BlankAnswerMapper blankAnswerMapper;

    @BeforeEach
    void setUp() {
        blankAnswerMapper = new BlankAnswerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBlankAnswerSample1();
        var actual = blankAnswerMapper.toEntity(blankAnswerMapper.toDto(expected));
        assertBlankAnswerAllPropertiesEquals(expected, actual);
    }
}
