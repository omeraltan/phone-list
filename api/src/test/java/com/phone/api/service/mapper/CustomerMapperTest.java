package com.phone.api.service.mapper;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.phone.api.domain.CustomerAsserts.assertCustomerAllPropertiesEquals;
import static com.phone.api.domain.CustomerTestSamples.getCustomerSample1;

public class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerMapper = new CustomerMapperImpl();
    }

    @Test
    @Description("Should test the bidirectional conversion of Customer and CustomerDTO using CustomerMapper.")
    public void shouldConvertToDtoToAndBack() {
        var expected = getCustomerSample1();
        var actual = customerMapper.toEntity(customerMapper.toDto(expected));
        assertCustomerAllPropertiesEquals(expected,actual);
    }
}
