package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TrainingProviderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TrainingProvider getTrainingProviderSample1() {
        return new TrainingProvider()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .phone("phone1")
            .avatarUrl("avatarUrl1")
            .organizationName("organizationName1")
            .website("website1")
            .accreditation("accreditation1");
    }

    public static TrainingProvider getTrainingProviderSample2() {
        return new TrainingProvider()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .phone("phone2")
            .avatarUrl("avatarUrl2")
            .organizationName("organizationName2")
            .website("website2")
            .accreditation("accreditation2");
    }

    public static TrainingProvider getTrainingProviderRandomSampleGenerator() {
        return new TrainingProvider()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .avatarUrl(UUID.randomUUID().toString())
            .organizationName(UUID.randomUUID().toString())
            .website(UUID.randomUUID().toString())
            .accreditation(UUID.randomUUID().toString());
    }
}
