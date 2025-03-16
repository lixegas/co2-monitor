package com.lixegas.co2_monitor.mapper;

import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.dto.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Named("cityMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface CityMapper {

    CityDTO toDto(City city);
}
