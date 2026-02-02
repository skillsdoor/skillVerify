package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BindListAnswerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BindListAnswer getBindListAnswerSample1() {
        return new BindListAnswer().id(1L).leftItemId(1L).rightItemId(1L);
    }

    public static BindListAnswer getBindListAnswerSample2() {
        return new BindListAnswer().id(2L).leftItemId(2L).rightItemId(2L);
    }

    public static BindListAnswer getBindListAnswerRandomSampleGenerator() {
        return new BindListAnswer()
            .id(longCount.incrementAndGet())
            .leftItemId(longCount.incrementAndGet())
            .rightItemId(longCount.incrementAndGet());
    }
}
