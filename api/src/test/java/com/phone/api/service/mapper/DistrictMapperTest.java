package com.phone.api.service.mapper;

import static com.phone.api.domain.DistrictAsserts.*;
import static com.phone.api.domain.DistrictTestSamples.*;

import com.phone.api.domain.District;
import com.phone.api.service.dto.DistrictDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class DistrictMapperTest {

    private DistrictMapper districtMapper;

    @BeforeEach
    void setUp() {
        districtMapper = new DistrictMapper() {
            @Override
            public District toEntity(DistrictDTO dto) {
                return null;
            }

            @Override
            public DistrictDTO toDto(District entity) {
                return null;
            }

            @Override
            public List<District> toEntity(List<DistrictDTO> dtoList) {
                return List.of();
            }

            @Override
            public List<DistrictDTO> toDto(List<District> entityList) {
                return List.of();
            }

            @Override
            public void partialUpdate(District entity, DistrictDTO dto) {

            }
        };
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDistrictSample1();
        var actual = districtMapper.toEntity(districtMapper.toDto(expected));
        assertDistrictAllPropertiesEquals(expected, actual);
    }
}
