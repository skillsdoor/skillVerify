package com.skillsdoor.skillverify.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MediaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Media getMediaSample1() {
        return new Media().id(1L).s3Key("s3Key1").s3Url("s3Url1").fileName("fileName1").fileSize(1L).mimeType("mimeType1");
    }

    public static Media getMediaSample2() {
        return new Media().id(2L).s3Key("s3Key2").s3Url("s3Url2").fileName("fileName2").fileSize(2L).mimeType("mimeType2");
    }

    public static Media getMediaRandomSampleGenerator() {
        return new Media()
            .id(longCount.incrementAndGet())
            .s3Key(UUID.randomUUID().toString())
            .s3Url(UUID.randomUUID().toString())
            .fileName(UUID.randomUUID().toString())
            .fileSize(longCount.incrementAndGet())
            .mimeType(UUID.randomUUID().toString());
    }
}
