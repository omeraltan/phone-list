package com.phone.api.service.mapper;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.phone.api.domain.PhoneAsserts.assertPhoneAllPropertiesEquals;
import static com.phone.api.domain.PhoneTestSamples.getPhoneSample1;
import static com.phone.api.domain.PhoneTestSamples.getPhoneSample3;

public class PhoneMapperTest {

    private PhoneMapper phoneMapper;
    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        phoneMapper = new PhoneMapperImpl();
        customerMapper = new CustomerMapperImpl();
    }

    @Test
    @Description("Should test the bidirectional conversion of Phone and PhoneDTO using PhoneMapper.")
    public void shouldConvertToDtoToAndBack() {
        var expected = getPhoneSample3();
        var actual = phoneMapper.toEntity(phoneMapper.toDto(expected));
        assertPhoneAllPropertiesEquals(expected, actual);
        // TODO: Burada kaldım.
    }

}
