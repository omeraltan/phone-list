package com.phone.api.service.mapper;

import com.phone.api.domain.Customer;
import com.phone.api.domain.Phone;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.PhoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Phone} and its DTO {@link PhoneDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhoneMapper extends EntityMapper<PhoneDTO, Phone> {

    @Mapping(target = "customerDTO", source = "customer", qualifiedByName = "customerId")
    PhoneDTO toDto(Phone s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Mapping(target = "customer", source = "customerDTO")
    Phone toEntity(PhoneDTO phoneDTO);
}
