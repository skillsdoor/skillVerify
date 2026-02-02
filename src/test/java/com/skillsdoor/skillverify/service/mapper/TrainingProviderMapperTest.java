package com.skillsdoor.skillverify.service.mapper;

import static com.skillsdoor.skillverify.domain.TrainingProviderAsserts.*;
import static com.skillsdoor.skillverify.domain.TrainingProviderTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingProviderMapperTest {

    private TrainingProviderMapper trainingProviderMapper;

    @BeforeEach
    void setUp() {
        trainingProviderMapper = new TrainingProviderMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTrainingProviderSample1();
        var actual = trainingProviderMapper.toEntity(trainingProviderMapper.toDto(expected));
        assertTrainingProviderAllPropertiesEquals(expected, actual);
    }
}
