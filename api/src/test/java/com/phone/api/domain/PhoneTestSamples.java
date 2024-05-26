package com.phone.api.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PhoneTestSamples {
    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Phone getPhoneSample1(){
        Customer customer = new Customer();
        customer.setId(longCount.incrementAndGet());
        return new Phone(1L, "(0312)-456-26-41", customer);
    }

    public static Phone getPhoneSample2(){
        Customer customer = new Customer();
        customer.setId(intCount.longValue());
        return new Phone(2L, "(0539)-763-67-88", customer);
    }
}
