package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.RecruiterAsserts.*;
import static com.skillsdoor.skillverify.domain.RecruiterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecruiterMapperTest {

    private RecruiterMapper recruiterMapper;

    @BeforeEach
    void setUp() {
        recruiterMapper = new RecruiterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRecruiterSample1();
        var actual = recruiterMapper.toEntity(recruiterMapper.toDto(expected));
        assertRecruiterAllPropertiesEquals(expected, actual);
    }
}
