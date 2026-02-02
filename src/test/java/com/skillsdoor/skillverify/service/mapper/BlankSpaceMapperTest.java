package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.BlankSpaceAsserts.*;
import static com.skillsdoor.skillverify.domain.BlankSpaceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlankSpaceMapperTest {

    private BlankSpaceMapper blankSpaceMapper;

    @BeforeEach
    void setUp() {
        blankSpaceMapper = new BlankSpaceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBlankSpaceSample1();
        var actual = blankSpaceMapper.toEntity(blankSpaceMapper.toDto(expected));
        assertBlankSpaceAllPropertiesEquals(expected, actual);
    }
}
