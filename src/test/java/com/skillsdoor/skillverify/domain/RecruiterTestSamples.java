package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecruiterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Recruiter getRecruiterSample1() {
        return new Recruiter()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .phone("phone1")
            .avatarUrl("avatarUrl1")
            .company("company1")
            .position("position1")
            .website("website1");
    }

    public static Recruiter getRecruiterSample2() {
        return new Recruiter()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .phone("phone2")
            .avatarUrl("avatarUrl2")
            .company("company2")
            .position("position2")
            .website("website2");
    }

    public static Recruiter getRecruiterRandomSampleGenerator() {
        return new Recruiter()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .avatarUrl(UUID.randomUUID().toString())
            .company(UUID.randomUUID().toString())
            .position(UUID.randomUUID().toString())
            .website(UUID.randomUUID().toString());
    }
}
