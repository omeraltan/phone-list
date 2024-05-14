package com.phone.api.service;

import com.phone.api.domain.Phone;
import com.phone.api.repository.PhoneRepository;
import com.phone.api.service.dto.PhoneDTO;
import com.phone.api.service.mapper.PhoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Phone}.
 */
@Service
@Transactional
public class PhoneService {

    private final Logger log = LoggerFactory.getLogger(PhoneService.class);

    private final PhoneMapper phoneMapper;
    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(PhoneMapper phoneMapper, PhoneRepository phoneRepository) {
        this.phoneMapper = phoneMapper;
        this.phoneRepository = phoneRepository;
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




}
