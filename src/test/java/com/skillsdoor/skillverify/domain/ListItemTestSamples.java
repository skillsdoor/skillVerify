package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ListItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ListItem getListItemSample1() {
        return new ListItem().id(1L).content("content1").matchingContent("matchingContent1").orderIndex(1);
    }

    public static ListItem getListItemSample2() {
        return new ListItem().id(2L).content("content2").matchingContent("matchingContent2").orderIndex(2);
    }

    public static ListItem getListItemRandomSampleGenerator() {
        return new ListItem()
            .id(longCount.incrementAndGet())
            .content(UUID.randomUUID().toString())
            .matchingContent(UUID.randomUUID().toString())
            .orderIndex(intCount.incrementAndGet());
    }
}
