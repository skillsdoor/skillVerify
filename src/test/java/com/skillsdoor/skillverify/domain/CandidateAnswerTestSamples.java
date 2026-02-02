package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CandidateAnswerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CandidateAnswer getCandidateAnswerSample1() {
        return new CandidateAnswer().id(1L).pointsAwarded(1);
    }

    public static CandidateAnswer getCandidateAnswerSample2() {
        return new CandidateAnswer().id(2L).pointsAwarded(2);
    }

    public static CandidateAnswer getCandidateAnswerRandomSampleGenerator() {
        return new CandidateAnswer().id(longCount.incrementAndGet()).pointsAwarded(intCount.incrementAndGet());
    }
}
