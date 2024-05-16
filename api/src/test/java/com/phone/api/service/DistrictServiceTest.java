package com.phone.api.service;

// USing BDD Mockito
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.phone.api.domain.District;
import com.phone.api.repository.DistrictRepository;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.mapper.DistrictMapper;
import com.phone.api.service.mapper.DistrictMapperImpl;
import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DistrictServiceTest {

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private DistrictRepository districtRepository;

    @InjectMocks
    private DistrictService districtService;

    @Spy
    private DistrictMapper districtMapperSpy = new DistrictMapperImpl();

    @Test
    @Description("Should create a district in the database")
    public void DistrictService_CreateDistrict_ReturnDistrictDTO(){
        District district = new District();
        DistrictDTO districtDTO = new DistrictDTO("Ankara", "Ankara Description", -1);
        BeanUtils.copyProperties(districtDTO,district);
        when(districtRepository.save(Mockito.any(District.class))).thenReturn(district);
        DistrictDTO savedDistrict = districtService.save(districtDTO);
        Assertions.assertThat(savedDistrict).isNotNull();
    }

    @Test
    @Description("Should fetch all the districts in the database")
    public void DistrictService_GetAllDistrict_ReturnDistrictDTO(){
        var district = Mockito.mock(Page.class);
        when(districtRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(district);
        districtService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "id"));
        verify(districtRepository).findAll(Mockito.any(PageRequest.class));
        verifyNoMoreInteractions(districtRepository);
    }

    @Test
    @Description("Should fetch a district with id parameter")
    public void DistrictService_GetDistrictById_ReturnDistrictDTO(){
        long districtId = 1L;
        Optional<District> district = Optional.ofNullable(Mockito.mock(District.class));
        when(districtRepository.findById(districtId)).thenReturn(district);
        Optional<District> result = districtRepository.findById(districtId);
        assertTrue(result.isPresent());
        assertSame(district, result);
    }

    @Test
    @Description("Should not return any district data")
    public void DistrictService_GetDistrictById_WhenDistrictNotExist_ReturnOptionalEmpty(){
        long districtId = 2L;
        when(districtRepository.findById(districtId)).thenReturn(Optional.empty());
        Optional<DistrictDTO> result = districtService.findOne(districtId);
        assertFalse(result.isPresent());
    }

    @Test
    @Description("Should update any district data")
    public void DistrictService_UpdateDistrict_ReturnDistrictDTO(){
        long districtId = 1L;
        DistrictDTO districtDTO = new DistrictDTO("Ankara", "Ankara Description", -1);
        District district = new District();
        BeanUtils.copyProperties(districtDTO,district);

        when(districtRepository.findById(districtId)).thenReturn(Optional.ofNullable(district));
        when(districtRepository.save(district)).thenReturn(district);

        DistrictDTO updateDistrict = districtService.update(districtDTO);

        Assertions.assertThat(updateDistrict).isNotNull();
    }

    @Test
    @Description("Should delete district data")
    public void DistrictService_DeleteDistrict_ReturnDistrictDTO(){
        long districtId = 1L;
        DistrictDTO districtDTO = new DistrictDTO("Ankara", "Ankara Description", -1);

        District district = new District();
        BeanUtils.copyProperties(districtDTO,district);
        when(districtRepository.findById(districtId)).thenReturn(Optional.ofNullable(district));
        doNothing().when(districtRepository).delete(district);
        districtService.delete(districtId);
        assertAll(() -> districtService.delete(districtId));
    }

    @After
    public void tearDown(){
        Mockito.reset(districtRepository);
    }

}
