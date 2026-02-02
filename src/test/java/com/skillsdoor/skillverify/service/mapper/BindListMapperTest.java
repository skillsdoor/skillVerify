package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.BindListAsserts.*;
import static com.skillsdoor.skillverify.domain.BindListTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BindListMapperTest {

    private BindListMapper bindListMapper;

    @BeforeEach
    void setUp() {
        bindListMapper = new BindListMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBindListSample1();
        var actual = bindListMapper.toEntity(bindListMapper.toDto(expected));
        assertBindListAllPropertiesEquals(expected, actual);
    }
}
