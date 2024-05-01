package com.phone.api.service;

// USing BDD Mockito
import static org.mockito.BDDMockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import com.phone.api.repository.DistrictRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DistrictServiceTest {

    @Mock
    private DistrictRepository districtRepository;
    @InjectMocks
    private DistrictService districtService;

//    @Test
//    void getAllDistrict() {
//        // given
//        District district1 = new District(1L,"Ankara", "Türkiyenin Başkentidir.", -1);
//        District district2 = new District(2L,"İstanbul", "Türkiyenin En Büyük Şehridir.", -1);
//
//        // when
//        given(districtRepository.findAll()).willReturn(List.of(district1,district2));
//        var districtList = districtService.findAll(Pageable.ofSize(0));
//
//        // then
//        assertThat(districtList).hasSize(2);
//        assertThat(districtList).isNotNull();
//        verify(districtRepository).findAll();
//    }
//
//    @Test
//    void getDistrictById() {
//        // given
//        District district = new District(1L, "Ankara", "Türkiyenin Başkentidir.", -1);
//
//        // when
//        given(districtRepository.findById(1L)).willReturn(List.of(district));
//
//
//    }
}
