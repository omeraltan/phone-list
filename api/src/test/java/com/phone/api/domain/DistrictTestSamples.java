package com.phone.api.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DistrictTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static District getDistrictSample1() {
        return new District(1L, "name1", "Description1", 1);
    }

    public static District getDistrictSample2() {
        return new District(2L, "name2", "Description2", 1);
    }

}
