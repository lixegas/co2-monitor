package com.lixegas.co2_monitor.mapper;

import com.lixegas.co2_monitor.model.District;
import com.lixegas.co2_monitor.model.dto.DistrictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("districtMapper")
@Mapper(componentModel = "spring")
public interface DistrictMapper {

    @Mapping(source = "city.id", target = "cityId")
    DistrictDTO toDto (District district);
}
