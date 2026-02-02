package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FillInTheBlankTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FillInTheBlank getFillInTheBlankSample1() {
        return new FillInTheBlank().id(1L).blankIndex(1).correctAnswer("correctAnswer1");
    }

    public static FillInTheBlank getFillInTheBlankSample2() {
        return new FillInTheBlank().id(2L).blankIndex(2).correctAnswer("correctAnswer2");
    }

    public static FillInTheBlank getFillInTheBlankRandomSampleGenerator() {
        return new FillInTheBlank()
            .id(longCount.incrementAndGet())
            .blankIndex(intCount.incrementAndGet())
            .correctAnswer(UUID.randomUUID().toString());
    }
}
