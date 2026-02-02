package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class QcmAnswerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static QcmAnswer getQcmAnswerSample1() {
        return new QcmAnswer().id(1L);
    }

    public static QcmAnswer getQcmAnswerSample2() {
        return new QcmAnswer().id(2L);
    }

    public static QcmAnswer getQcmAnswerRandomSampleGenerator() {
        return new QcmAnswer().id(longCount.incrementAndGet());
    }
}
