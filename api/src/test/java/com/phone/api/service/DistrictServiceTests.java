package com.phone.api.service;

// USing BDD Mockito
import static javax.swing.SortOrder.UNSORTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.phone.api.domain.District;
import com.phone.api.repository.DistrictRepository;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.mapper.DistrictMapper;
import com.phone.api.web.rest.DistrictResource;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class DistrictServiceTests {

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    // Creating a mock to mimic DistrictRepository.
    @Mock
    private DistrictRepository districtRepository;
    // If you don't inject mapper class. You will take null pointer for this.
    @Mock
    private DistrictMapper districtMapper;

    // An instance of DistrictService, injected with the Mock created with @Mock.
    @InjectMocks
    private DistrictService districtService;

    // A test method to test the create method of the DistrictService class.
    @Test
    public void DistrictService_CreateDistrict_ReturnDistrictDTO(){
        // Creating an instance of District to be used for the test.
        District district = new District();
        // Creating an instance of DistrictDto to be used for the test.
        DistrictDTO districtDTO = DistrictDTO.builder()
            .name("Ankara")
            .description("Ankara Description")
            .code(-1)
            .build();

        // Using BeanUtils.copyProperties to copy properties from DistrictDto to District for the test.
        BeanUtils.copyProperties(districtDTO,district);
        // Mocking the districtRepository.save method to return this instance of District whenever any instance of District is passed.
        when(districtRepository.save(Mockito.any(District.class))).thenReturn(district);
        // Calling the create method of DistrictService and checking if it returns the expected instance of DistrictDto.
        DistrictDTO savedDistrict = districtService.save(districtDTO);
        // Using AssertJ to check the correctness of the expected values.
        Assertions.assertThat(savedDistrict).isNotNull();
    }

    @Test
    public void DistrictService_GetAllDistrict_ReturnDistrictDTO(){
        Page<District> districts = Mockito.mock(Page.class);
        when(districtRepository.findAll(Mockito.any(Pageable.class))).thenReturn(districts);
        Page<DistrictDTO> page = districtService.findAll(PageRequest.of(0, 10));
        Assertions.assertThat(page).isNotNull();

    }

    @After
    public void tearDown(){
        Mockito.reset(districtRepository);
    }

}
