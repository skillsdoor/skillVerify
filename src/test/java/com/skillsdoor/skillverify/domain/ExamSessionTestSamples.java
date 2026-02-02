package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ExamSessionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ExamSession getExamSessionSample1() {
        return new ExamSession().id(1L).score(1).totalPoints(1).timeSpent(1);
    }

    public static ExamSession getExamSessionSample2() {
        return new ExamSession().id(2L).score(2).totalPoints(2).timeSpent(2);
    }

    public static ExamSession getExamSessionRandomSampleGenerator() {
        return new ExamSession()
            .id(longCount.incrementAndGet())
            .score(intCount.incrementAndGet())
            .totalPoints(intCount.incrementAndGet())
            .timeSpent(intCount.incrementAndGet());
    }
}
