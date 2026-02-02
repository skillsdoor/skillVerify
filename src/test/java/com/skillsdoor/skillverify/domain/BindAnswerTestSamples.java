package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BindAnswerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BindAnswer getBindAnswerSample1() {
        return new BindAnswer().id(1L).leftItemId(1L).rightItemId(1L);
    }

    public static BindAnswer getBindAnswerSample2() {
        return new BindAnswer().id(2L).leftItemId(2L).rightItemId(2L);
    }

    public static BindAnswer getBindAnswerRandomSampleGenerator() {
        return new BindAnswer()
            .id(longCount.incrementAndGet())
            .leftItemId(longCount.incrementAndGet())
            .rightItemId(longCount.incrementAndGet());
    }
}
