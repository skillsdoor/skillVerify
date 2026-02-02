package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BlankSpaceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static BlankSpace getBlankSpaceSample1() {
        return new BlankSpace().id(1L).position(1).correctAnswer("correctAnswer1").orderIndex(1);
    }

    public static BlankSpace getBlankSpaceSample2() {
        return new BlankSpace().id(2L).position(2).correctAnswer("correctAnswer2").orderIndex(2);
    }

    public static BlankSpace getBlankSpaceRandomSampleGenerator() {
        return new BlankSpace()
            .id(longCount.incrementAndGet())
            .position(intCount.incrementAndGet())
            .correctAnswer(UUID.randomUUID().toString())
            .orderIndex(intCount.incrementAndGet());
    }
}
