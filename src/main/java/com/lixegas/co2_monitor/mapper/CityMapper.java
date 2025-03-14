package com.lixegas.co2_monitor.mapper;

import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.dto.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("cityMapper")
@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "user", target = "user")
    CityDTO toDto(City city);

    @Mapping(source = "user", target = "user")
    City toEntity(CityDTO cityDTO);
}
