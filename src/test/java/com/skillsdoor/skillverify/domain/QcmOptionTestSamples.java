package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class QcmOptionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static QcmOption getQcmOptionSample1() {
        return new QcmOption().id(1L).content("content1").orderIndex(1);
    }

    public static QcmOption getQcmOptionSample2() {
        return new QcmOption().id(2L).content("content2").orderIndex(2);
    }

    public static QcmOption getQcmOptionRandomSampleGenerator() {
        return new QcmOption().id(longCount.incrementAndGet()).content(UUID.randomUUID().toString()).orderIndex(intCount.incrementAndGet());
    }
}
