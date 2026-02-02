package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.ListItemAsserts.*;
import static com.skillsdoor.skillverify.domain.ListItemTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListItemMapperTest {

    private ListItemMapper listItemMapper;

    @BeforeEach
    void setUp() {
        listItemMapper = new ListItemMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getListItemSample1();
        var actual = listItemMapper.toEntity(listItemMapper.toDto(expected));
        assertListItemAllPropertiesEquals(expected, actual);
    }
}
