package com.phone.api.service;

import com.phone.api.domain.Phone;
import com.phone.api.repository.CustomerRepository;
import com.phone.api.repository.PhoneRepository;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.PhoneDTO;
import com.phone.api.service.mapper.CustomerMapper;
import com.phone.api.service.mapper.PhoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Phone}.
 */
@Service
@Transactional
public class PhoneService {

    private final Logger log = LoggerFactory.getLogger(PhoneService.class);

    private final PhoneMapper phoneMapper;
    private final PhoneRepository phoneRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public PhoneService(PhoneMapper phoneMapper, PhoneRepository phoneRepository, CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.phoneMapper = phoneMapper;
        this.phoneRepository = phoneRepository;
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Save a phone number
     *
     * @param phoneDTO the entity to save
     * @return the persisted entity.
     */
    public PhoneDTO save(PhoneDTO phoneDTO) {
        log.debug("Request to save Phone : {}", phoneDTO);
        Phone phone = phoneMapper.toEntity(phoneDTO);
        phone = phoneRepository.save(phone);
        return phoneMapper.toDto(phone);
    }

    public Optional<List<CustomerDTO>> findAll() {
        log.debug("Request to get all Customers");
        List<CustomerDTO> customerDTOS = customerMapper.toDto(customerRepository.findAll());
        return customerDTOS == null ? Optional.empty() : Optional.of(customerDTOS);
    }
}
