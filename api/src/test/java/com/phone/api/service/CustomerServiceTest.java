package com.phone.api.service;

import com.phone.api.domain.Customer;
import com.phone.api.domain.District;
import com.phone.api.repository.CustomerRepository;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.mapper.CustomerMapper;
import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @Description("Should fetch a customer with id parameter")
    public void CustomerService_FindOneCustomer_ReturnCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        Optional<CustomerDTO> result = customerService.findOne(1L);
        assertTrue(result.isPresent());
        assertEquals(customerDTO, result.get());
    }

    @Test
    @Description("Should fetch all customers")
    public void CustomerService_FindAllCustomers_ReturnCustomerDTOList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customersPage = new PageImpl<>(Collections.emptyList());
        when(customerRepository.findAll(pageable)).thenReturn(customersPage);
        Page<CustomerDTO> result = customerService.findAll(pageable);
        assertNotNull(result);
        assertEquals(0, result.getContent().size());
    }

    @Test
    @Description("Should create a customer in the database")
    public void CustomerService_CreateCustomer_ReturnCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO(1L, "Ömer", "ALTAN", "omer@gmail.com", "Saraycık Mahallesi 2432.Cadde", new DistrictDTO(1L));
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        District district = new District();
        district.setId(1L);
        customer.setDistrict(district);

        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        CustomerDTO savedCustomer = customerService.save(customerDTO);
        Assertions.assertThat(savedCustomer).isNotNull();
        assertEquals(customerDTO.getId(), savedCustomer.getId());
        assertEquals(customerDTO.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customerDTO.getLastName(), savedCustomer.getLastName());
        assertEquals(customerDTO.getEmail(), savedCustomer.getEmail());
        assertEquals(customerDTO.getAddress(), savedCustomer.getAddress());
        assertNotNull(savedCustomer.getDistrictDTO());
        assertEquals(customerDTO.getDistrictDTO().getId(), savedCustomer.getDistrictDTO().getId());
    }

    @Test
    @Description("Should update a customer in the database")
    public void DistrictService_UpdateCustomer_ReturnDistrictDTO() {
        CustomerDTO customerDTO = new CustomerDTO(1L, "Ömer", "ALTAN", "omeraltan66@gmail.com", "Saraycık Mahallesi 2432.Cadde", new DistrictDTO(1L));
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        District district = new District();
        district.setId(1L);
        customer.setDistrict(district);

        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.update(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.getId(), result.getId());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        assertEquals(customerDTO.getEmail(), result.getEmail());
        assertEquals(customerDTO.getAddress(), result.getAddress());
        assertNotNull(result.getDistrictDTO());
        assertEquals(customerDTO.getDistrictDTO().getId(), result.getDistrictDTO().getId());
    }

    @Test
    @Description("Should delete a customer in the database")
    public void CustomerService_DeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);
        customerService.delete(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }
}
