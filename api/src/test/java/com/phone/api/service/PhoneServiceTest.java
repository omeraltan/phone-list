package com.phone.api.service;

import com.phone.api.domain.Customer;
import com.phone.api.domain.District;
import com.phone.api.domain.Phone;
import com.phone.api.repository.CustomerRepository;
import com.phone.api.repository.PhoneRepository;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.dto.PhoneDTO;
import com.phone.api.service.mapper.CustomerMapper;
import com.phone.api.service.mapper.PhoneMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock CustomerMapper customerMapper;

    @Mock
    private PhoneMapper phoneMapper;

    @InjectMocks
    private PhoneService phoneService;

    @Test
    @Description("Should return count of phones for a customer")
    public void PhoneService_FindCountOfPhonesForCustomer_ReturnCount() {
        Long customerId = 1L;
        Integer expectedCount = 5;
        when(phoneRepository.findCountByCustomerPhones(customerId)).thenReturn(expectedCount);
        Integer actualCount = phoneService.getCountPhones(customerId);
        assertEquals(expectedCount, actualCount);
    }

    @Test
    @Description("Should fetch all phones")
    public void PhoneService_FindAllPhones_ReturnPhoneDTO() {
        Customer customer1 = new Customer(1L);
        Customer customer2 = new Customer(2L);
        List<Phone> phones = Arrays.asList(
            new Phone(1L, "1234567890", customer1),
            new Phone(2L, "9876543210", customer2)
        );
        CustomerDTO customer3 = new CustomerDTO(3L);
        CustomerDTO customer4 = new CustomerDTO(4L);
        List<PhoneDTO> phoneDTOs = Arrays.asList(
            new PhoneDTO(1L, "1234567890", customer3),
            new PhoneDTO(2L, "9876543210", customer4)
        );
        Pageable pageable = PageRequest.of(0, 10);
        Page<Phone> phonesPage = new PageImpl<>(phones);
        when(phoneRepository.findAll(pageable)).thenReturn(phonesPage);
        when(phoneMapper.toDto(phones)).thenReturn(phoneDTOs);
        Page<PhoneDTO> result = phoneService.findAll(pageable);
        assertNotNull(result);
        assertEquals(phones.size(), result.getContent().size());
    }

    @Test
    @Description("Should fetch all customers and return a list of CustomerDTOs")
    public void PhoneService_AllCustomers_ReturnCustomerDTO() {
        List<Customer> customers = Arrays.asList(
            new Customer(1L, "John", "Doe", "john.doe@example.com", new District(1L)),
            new Customer(2L, "Jane", "Smith", "jane.smith@example.com", new District(2L))
        );
        List<CustomerDTO> customerDTOs = Arrays.asList(
            new CustomerDTO(1L, "John", "Doe", "john.doe@example.com", "123 Main St", new DistrictDTO(1L)),
            new CustomerDTO(2L, "Jane", "Smith", "jane.smith@example.com", "456 Elm St", new DistrictDTO(2L))
        );
        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.toDto(customers)).thenReturn(customerDTOs);
        Optional<List<CustomerDTO>> result = phoneService.findAll();
        assertTrue(result.isPresent());
        assertEquals(customerDTOs.size(), result.get().size());
        assertEquals(customerDTOs.get(0), result.get().get(0));
        assertEquals(customerDTOs.get(1), result.get().get(1));
    }

    @Test
    @Description("Should create a phone in the database")
    public void PhoneService_CreatePhone_ReturnPhoneDTO() {
        CustomerDTO customer1 = new CustomerDTO(1L);
        Customer customer2 = new Customer(2L);
        PhoneDTO phoneDTO = new PhoneDTO(1L, "1234567890", customer1);
        Phone phone = new Phone();
        phone.setId(1L);
        phone.setPhoneNumber("1234567890");
        phone.setCustomer(customer2);
        when(phoneMapper.toEntity(phoneDTO)).thenReturn(phone);
        when(phoneRepository.save(phone)).thenReturn(phone);
        when(phoneMapper.toDto(phone)).thenReturn(phoneDTO);
        PhoneDTO savedPhone = phoneService.save(phoneDTO);
        assertNotNull(savedPhone);
        assertEquals(phoneDTO.getId(), savedPhone.getId());
        assertEquals(phoneDTO.getPhoneNumber(), savedPhone.getPhoneNumber());
        assertEquals(phoneDTO.getCustomerDTO(), savedPhone.getCustomerDTO());
    }

    @Test
    @Description("Should delete a phone in the database")
    public void PhoneService_DeletePhone() {
        Long phoneId = 1L;
        phoneService.delete(phoneId);
        verify(phoneRepository, times(1)).deleteById(phoneId);
    }
}

