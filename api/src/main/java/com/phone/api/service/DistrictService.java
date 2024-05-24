package com.phone.api.service;

import com.phone.api.domain.District;
import com.phone.api.repository.DistrictRepository;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.mapper.DistrictMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link District}.
 */
@Service
@Transactional
public class DistrictService {

    private final Logger log = LoggerFactory.getLogger(DistrictService.class);

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;


    public DistrictService(DistrictRepository districtRepository, DistrictMapper districtMapper) {
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
    }

    /**
     * Get all the phones.
     * @param districtId the count information.
     * @return the count of district.
     */
    public Integer getCountDistricts(Long districtId){
        log.debug("Request getCountDistricts districtId : {}", districtId);
        return districtRepository.findCountByDistrictCustomers(districtId);
    }

    /**
     * Get one district by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DistrictDTO> findOne(Long id) {
        log.debug("Request to get District : {}", id);
        return districtRepository.findById(id).map(districtMapper::toDto);
    }

    /**
     * Get all the cities. Actually, we take the districts with code.
     *
     * @param code the state information.
     * @return the list of entities.
     */
    @Transactional
    public Optional<List<DistrictDTO>> findDistrictsByCodeIsLessThanZero(int code) {
        log.debug("Request to get District By Code Less Then Zero : {}", code);
        List<District> district = districtRepository.findDistrictsByCodeIsLessThan(code);
        List<DistrictDTO> districtDTO = districtMapper.toDto(district);
        return districtDTO == null ? Optional.empty() : Optional.of(districtDTO);
    }

    /**
     * Get all the districts. Actually, we take the districts with code.
     *
     * @param code the state information.
     * @return the list of entities.
     */
    @Transactional
    public Optional<List<DistrictDTO>> findDistrictsByCodeIsGreaterThanZero(int code) {
        log.debug("Request to get District By Code Greater Then Zero : {}", code);
        List<District> districts = districtRepository.findDistrictsByCodeIsGreaterThanZero(code);
        List<DistrictDTO> districtDTO = districtMapper.toDto(districts);
        return districtDTO == null ? Optional.empty() : Optional.of(districtDTO);
    }

    /**
     * Get all the districts amount.
     * @param code the count information.
     * @return the count of districts.
     */
    @Transactional
    public Integer getCountDistrictsOfCity(Integer code){
        log.debug("Request to get District By City : {}", code);
        return districtRepository.FindTheNumberOfDistrictsUsingThisCity(code);
    }

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DistrictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return districtRepository.findAll(pageable).map(districtMapper::toDto);
    }

    /**
     * Save a district.
     *
     * @param districtDTO the entity to save.
     * @return the persisted entity.
     */
    public DistrictDTO save(DistrictDTO districtDTO) {
        log.debug("Request to save District : {}", districtDTO);
        District district = districtMapper.toEntity(districtDTO);
        district = districtRepository.save(district);
        return districtMapper.toDto(district);
    }

    /**
     * Update a district.
     *
     * @param districtDTO the entity to save.
     * @return the persisted entity.
     */
    public DistrictDTO update(DistrictDTO districtDTO) {
        log.debug("Request to update District : {}", districtDTO);
        District district = districtMapper.toEntity(districtDTO);
        district = districtRepository.save(district);
        return districtMapper.toDto(district);
    }

    /**
     * Partially update a district.
     *
     * @param districtDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DistrictDTO> partialUpdate(DistrictDTO districtDTO) {
        log.debug("Request to partially update District : {}", districtDTO);

        return districtRepository
            .findById(districtDTO.getId())
            .map(existingDistrict -> {
                districtMapper.partialUpdate(existingDistrict, districtDTO);

                return existingDistrict;
            })
            .map(districtRepository::save)
            .map(districtMapper::toDto);
    }

    /**
     * Delete the district by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete District : {}", id);
        districtRepository.deleteById(id);
    }
}
