package com.phone.api.service.mapper.mapperImpl;

import com.phone.api.domain.District;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.service.mapper.DistrictMapper;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-25T15:54:47+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)

@Component
public class DistrictMapperImpl implements DistrictMapper {

    @Override
    public District toEntity(DistrictDTO dto) {
        if ( dto == null ) {
            return null;
        }

        District district = new District();

        district.setId( dto.getId() );
        district.setName( dto.getName() );
        district.setCode( dto.getCode() );

        return district;
    }

    @Override
    public DistrictDTO toDto(District entity) {
        if ( entity == null ) {
            return null;
        }

        DistrictDTO districtDTO = new DistrictDTO();

        districtDTO.setId( entity.getId() );
        districtDTO.setName( entity.getName() );
        districtDTO.setCode( entity.getCode() );

        return districtDTO;
    }

    @Override
    public List<District> toEntity(List<DistrictDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<District> list = new ArrayList<District>( dtoList.size() );
        for ( DistrictDTO districtDTO : dtoList ) {
            list.add( toEntity( districtDTO ) );
        }

        return list;
    }

    @Override
    public List<DistrictDTO> toDto(List<District> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DistrictDTO> list = new ArrayList<DistrictDTO>( entityList.size() );
        for ( District district : entityList ) {
            list.add( toDto( district ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(District entity, DistrictDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
    }
}
