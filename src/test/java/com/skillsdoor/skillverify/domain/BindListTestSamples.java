package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BindListTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static BindList getBindListSample1() {
        return new BindList().id(1L).leftItem("leftItem1").rightItem("rightItem1").orderIndex(1);
    }

    public static BindList getBindListSample2() {
        return new BindList().id(2L).leftItem("leftItem2").rightItem("rightItem2").orderIndex(2);
    }

    public static BindList getBindListRandomSampleGenerator() {
        return new BindList()
            .id(longCount.incrementAndGet())
            .leftItem(UUID.randomUUID().toString())
            .rightItem(UUID.randomUUID().toString())
            .orderIndex(intCount.incrementAndGet());
    }
}
