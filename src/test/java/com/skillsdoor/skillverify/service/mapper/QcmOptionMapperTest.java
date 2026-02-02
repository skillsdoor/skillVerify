package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.QcmOptionAsserts.*;
import static com.skillsdoor.skillverify.domain.QcmOptionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QcmOptionMapperTest {

    private QcmOptionMapper qcmOptionMapper;

    @BeforeEach
    void setUp() {
        qcmOptionMapper = new QcmOptionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getQcmOptionSample1();
        var actual = qcmOptionMapper.toEntity(qcmOptionMapper.toDto(expected));
        assertQcmOptionAllPropertiesEquals(expected, actual);
    }
}
