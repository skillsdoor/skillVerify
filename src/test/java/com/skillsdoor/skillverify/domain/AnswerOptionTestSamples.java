package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AnswerOptionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AnswerOption getAnswerOptionSample1() {
        return new AnswerOption().id(1L).content("content1").orderIndex(1);
    }

    public static AnswerOption getAnswerOptionSample2() {
        return new AnswerOption().id(2L).content("content2").orderIndex(2);
    }

    public static AnswerOption getAnswerOptionRandomSampleGenerator() {
        return new AnswerOption()
            .id(longCount.incrementAndGet())
            .content(UUID.randomUUID().toString())
            .orderIndex(intCount.incrementAndGet());
    }
}
