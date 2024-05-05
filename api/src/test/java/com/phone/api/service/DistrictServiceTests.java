package com.phone.api.service;

// USing BDD Mockito
import static javax.swing.SortOrder.UNSORTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.phone.api.domain.District;
import com.phone.api.repository.DistrictRepository;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.mapper.DistrictMapper;
import com.phone.api.web.rest.DistrictResource;
import jdk.jfr.Description;
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

import java.util.Optional;

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

    @Test
    @Description("")
    public void DistrictService_CreateDistrict_ReturnDistrictDTO(){
        District district = new District();
        DistrictDTO districtDTO = DistrictDTO.builder()
            .name("Ankara")
            .description("Ankara Description")
            .code(-1)
            .build();
        BeanUtils.copyProperties(districtDTO,district);
        when(districtRepository.save(Mockito.any(District.class))).thenReturn(district);
        DistrictDTO savedDistrict = districtService.save(districtDTO);
        Assertions.assertThat(savedDistrict).isNotNull();
    }

    @Test
    @Description("")
    public void DistrictService_GetAllDistrict_ReturnDistrictDTO(){
        Page<District> districts = Mockito.mock(Page.class);
        when(districtRepository.findAll(Mockito.any(Pageable.class))).thenReturn(districts);
        Page<DistrictDTO> page = districtService.findAll(PageRequest.of(0, 10));
        Assertions.assertThat(page).isNotNull();
    }

    @Test
    @Description("")
    public void DistrictService_GetDistrictById_ReturnDistrictDTO(){
        long districtId = 1L;
        District mockDistrict = Mockito.mock(District.class);
        when(districtRepository.findById(districtId)).thenReturn(Optional.of(mockDistrict));
        Optional<DistrictDTO> result = districtService.findOne(districtId);
        assertTrue(result.isPresent());
        assertSame(mockDistrict, result.get());
    }

    @Test
    @Description("")
    public void DistrictService_GetDistrictById_WhenDistrictNotExist_ReturnOptionalEmpty(){
        long districtId = 2L;
        when(districtRepository.findById(districtId)).thenReturn(Optional.empty());
        Optional<DistrictDTO> result = districtService.findOne(districtId);
        assertFalse(result.isPresent());
    }

    /**
     * @Test
     * public void EmployeeService_UpdateEmployee_ReturnsEmployeeDto(){
     *     int employeeId=1;
     *     Employee employee = Employee.builder()
     *             .id(employeeId).name("Aziz KAle")
     *             .dob(new Date()).gender("Male")
     *             .department("Tesing").build();
     *
     *     EmployeeDto employeeDto = employeeService.mapToDto(employee);
     *     when(employeeRepository.findById(employeeId)).thenReturn(Optional.ofNullable(employee));
     *     when(employeeRepository.save(employee)).thenReturn(employee);
     *
     *     EmployeeDto updatedEmployee = employeeService.update(employeeDto,employeeId);
     *
     *     Assertions.assertThat(updatedEmployee).isNotNull();
     * }
     *
     *
     * @Test
     * public void EmployeeService_DeleteEmployee_ReturnsVoid() {
     *     int employeeId = 1;
     *     Employee employee = Employee.builder()
     *             .id(employeeId).name("Aziz KAle")
     *             .dob(new Date()).gender("Male")
     *             .department("Tesing").build();
     *
     *     // When findById is invoked with the specified employeeId, it returns an Optional containing the employee.
     *     when(employeeRepository.findById(employeeId)).thenReturn(Optional.ofNullable(employee));
     *
     *     // Configure the delete method to perform no action when called with an Employee object.
     *     doNothing().when(employeeRepository).delete(employee);
     *
     *     // Invoke the delete method of the employeeService with the created employeeId.
     *     employeeService.delete(employeeId);
     *
     *     // Use assertAll to ensure that no exceptions are thrown during the execution of the delete method.
     *     assertAll(() -> employeeService.delete(employeeId));
     * }
     *
     */

    @After
    public void tearDown(){
        Mockito.reset(districtRepository);
    }

}
