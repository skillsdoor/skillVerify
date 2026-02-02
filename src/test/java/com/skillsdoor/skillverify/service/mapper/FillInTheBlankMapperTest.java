package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.FillInTheBlankAsserts.*;
import static com.skillsdoor.skillverify.domain.FillInTheBlankTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FillInTheBlankMapperTest {

    private FillInTheBlankMapper fillInTheBlankMapper;

    @BeforeEach
    void setUp() {
        fillInTheBlankMapper = new FillInTheBlankMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFillInTheBlankSample1();
        var actual = fillInTheBlankMapper.toEntity(fillInTheBlankMapper.toDto(expected));
        assertFillInTheBlankAllPropertiesEquals(expected, actual);
    }
}
