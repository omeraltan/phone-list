package com.phone.api.service;

import com.phone.api.domain.Customer;
import com.phone.api.domain.District;
import com.phone.api.repository.CustomerRepository;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.mapper.CustomerMapper;
import com.phone.api.service.mapper.CustomerMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
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
    private CustomerMapper customerMapper;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Spy
    CustomerMapper customerMapperSpy = new CustomerMapperImpl();

    @Test
    public void testFindOne() {
        // Test için bir örnek müşteri oluşturun
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        // Müşteri ID'sine göre mock bir CustomerDTO oluşturun
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());

        // customerRepository.findById(id) çağrısına verilecek cevabı belirleyin
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // customerMapper.toDto(customer) çağrısına verilecek cevabı belirleyin
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        // Servis metodu çağırın
        Optional<CustomerDTO> result = customerService.findOne(1L);

        // Sonucu kontrol edin
        assertTrue(result.isPresent());
        assertEquals(customerDTO, result.get());
    }

    @Test
    public void testFindAll() {
        // Test için bir sayfalama nesnesi oluşturun
        Pageable pageable = PageRequest.of(0, 10);

        // customerRepository.findAll(pageable) çağrısına verilecek cevabı belirleyin
        Page<Customer> customersPage = new PageImpl<>(Collections.emptyList());
        when(customerRepository.findAll(pageable)).thenReturn(customersPage);

        // Servis metodu çağırın
        Page<CustomerDTO> result = customerService.findAll(pageable);

        // Sonucu kontrol edin
        assertNotNull(result);
        assertEquals(0, result.getContent().size());
    }

    @Test
    public void testSave() {
        // Test için bir müşteri DTO oluşturun
        DistrictDTO districtDTO = new DistrictDTO();
        districtDTO.setId(1L);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setEmail("john@doe.com");
        customerDTO.setDistrictDTO(districtDTO);

        // customerMapper.toEntity(customerDTO) çağrısına verilecek cevabı belirleyin
        Customer customer = new Customer();
        District district = new District();
        district.setId(1L);
        customer.setId(customerDTO.getId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setDistrict(district);
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);

        // customerRepository.save(customer) çağrısına verilecek cevabı belirleyin
        when(customerRepository.save(customer)).thenReturn(customer);

        // Servis metodu çağırın
        CustomerDTO result = customerService.save(customerDTO);

        // Sonucu kontrol edin
        assertNotNull(result);
        assertEquals(customerDTO, result);
    }

    @Test
    public void testUpdate() {
        // Test için bir müşteri DTO oluşturun
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");

        // customerMapper.toEntity(customerDTO) çağrısına verilecek cevabı belirleyin
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);

        // customerRepository.save(customer) çağrısına verilecek cevabı belirleyin
        when(customerRepository.save(customer)).thenReturn(customer);

        // Servis metodu çağırın
        CustomerDTO result = customerService.update(customerDTO);

        // Sonucu kontrol edin
        assertNotNull(result);
        assertEquals(customerDTO, result);
    }

    @Test
    public void testDelete() {
        // customerRepository.deleteById(id) çağrısını doğrulayın
        doNothing().when(customerRepository).deleteById(1L);

        // Servis metodu çağırın
        customerService.delete(1L);

        // Metodun çağrılıp çağrılmadığını kontrol edin
        verify(customerRepository, times(1)).deleteById(1L);
    }
}
