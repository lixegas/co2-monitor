package com.lixegas.co2_monitor.mapper;

import com.lixegas.co2_monitor.model.Sensor;
import com.lixegas.co2_monitor.model.dto.SensorDTO;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("sensorMapper")
@Mapper(componentModel = "spring")
public interface SensorMapper {

    @Mapping(source = "district.id", target = "districtId")
    SensorDTO toDto(Sensor sensor);
}
