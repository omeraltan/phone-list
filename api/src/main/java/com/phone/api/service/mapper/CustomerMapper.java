package com.phone.api.service.mapper;


import com.phone.api.domain.Customer;
import com.phone.api.domain.District;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.dto.PhoneDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    @Mapping(target = "districtDTO", source = "district", qualifiedByName = "districtId")
    CustomerDTO toDto(Customer s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Mapping(target = "district", source = "districtDTO")
    Customer toEntity(CustomerDTO customerDTO);

}
