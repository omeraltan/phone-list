package com.phone.api.service.mapper;

import static com.phone.api.domain.DistrictAsserts.*;
import static com.phone.api.domain.DistrictTestSamples.*;

import com.phone.api.service.mapper.mapperImpl.DistrictMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistrictMapperTest {

    private DistrictMapper districtMapper;

    @BeforeEach
    void setUp() {
        districtMapper = new DistrictMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDistrictSample1();
        var actual = districtMapper.toEntity(districtMapper.toDto(expected));
        assertDistrictAllPropertiesEquals(expected, actual);
    }
}
