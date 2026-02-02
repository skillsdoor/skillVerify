package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BlankAnswerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BlankAnswer getBlankAnswerSample1() {
        return new BlankAnswer().id(1L).answerText("answerText1");
    }

    public static BlankAnswer getBlankAnswerSample2() {
        return new BlankAnswer().id(2L).answerText("answerText2");
    }

    public static BlankAnswer getBlankAnswerRandomSampleGenerator() {
        return new BlankAnswer().id(longCount.incrementAndGet()).answerText(UUID.randomUUID().toString());
    }
}
