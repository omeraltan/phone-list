package com.phone.api.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerTestSamples {
    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Customer getCustomerSample1(){
        return new Customer(1L, "name1".toUpperCase(), "lastname1".toUpperCase(), "name1@gmail.com".toUpperCase(), "address address1".toUpperCase(), new District(1L));
    }

    public static Customer getCustomerSample2(){
        return new Customer(2L, "name2", "lastname2", "name2@gmail.com", new District());
    }
}
