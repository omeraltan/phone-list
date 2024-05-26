package com.phone.api.service.mapper;

import static com.phone.api.domain.DistrictAsserts.*;
import static com.phone.api.domain.DistrictTestSamples.*;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistrictMapperTest {

    private DistrictMapper districtMapper;

    @BeforeEach
    void setUp() {
        districtMapper = new DistrictMapperImpl();
    }

    @Test
    @Description("Should test the bidirectional conversion of District and DistrictDTO using DistrictMapper.")
    void shouldConvertToDtoAndBack() {
        var expected = getDistrictSample1();
        var actual = districtMapper.toEntity(districtMapper.toDto(expected));
        assertDistrictAllPropertiesEquals(expected, actual);
    }
}
