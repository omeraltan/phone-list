package com.phone.api.service.mapper;


import com.phone.api.domain.Customer;
import com.phone.api.domain.District;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.DistrictDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    CustomerDTO toDto(Customer s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

}
