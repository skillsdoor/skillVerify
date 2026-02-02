package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.ExerciseAsserts.*;
import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExerciseMapperTest {

    private ExerciseMapper exerciseMapper;

    @BeforeEach
    void setUp() {
        exerciseMapper = new ExerciseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getExerciseSample1();
        var actual = exerciseMapper.toEntity(exerciseMapper.toDto(expected));
        assertExerciseAllPropertiesEquals(expected, actual);
    }
}
