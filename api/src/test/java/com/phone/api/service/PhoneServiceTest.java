package com.phone.api.service;

import com.phone.api.domain.Customer;
import com.phone.api.domain.Phone;
import com.phone.api.repository.CustomerRepository;
import com.phone.api.repository.PhoneRepository;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.PhoneDTO;
import com.phone.api.service.mapper.CustomerMapper;
import com.phone.api.service.mapper.PhoneMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PhoneMapper phoneMapper;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private PhoneService phoneService;

    @Test
    @Description("Test to verify saving a phone")
    public void testSavePhone() {
        // Mock phoneDTO
        PhoneDTO phoneDTO = new PhoneDTO();
        // Mock phone entity
        Phone phone = new Phone();

        // Mock toEntity and save methods in phoneMapper and phoneRepository
        when(phoneMapper.toEntity(phoneDTO)).thenReturn(phone);
        when(phoneRepository.save(phone)).thenReturn(phone);
        when(phoneMapper.toDto(phone)).thenReturn(phoneDTO);

        // Call the method to be tested
        PhoneDTO savedPhoneDTO = phoneService.save(phoneDTO);

        // Assertions
        assertNotNull(savedPhoneDTO);
        // Add more assertions as needed
    }

    @Test
    @Description("Test to verify finding all customers")
    public void testFindAllCustomers() {
        // Mock customerDTO list
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        // Mock customer entity list
        List<Customer> customerList = new ArrayList<>();

        // Mock toDto and findAll methods in customerMapper and customerRepository
        when(customerMapper.toDto(customerList)).thenReturn(customerDTOList);
        when(customerRepository.findAll()).thenReturn(customerList);

        // Call the method to be tested
        Optional<List<CustomerDTO>> result = phoneService.findAll();

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(customerDTOList, result.get());
        // Add more assertions as needed
    }


}
