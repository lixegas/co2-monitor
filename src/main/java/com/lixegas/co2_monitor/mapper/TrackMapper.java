package com.lixegas.co2_monitor.mapper;

import com.lixegas.co2_monitor.model.Track;
import com.lixegas.co2_monitor.model.dto.TrackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("trackMapper")
@Mapper(componentModel = "spring")
public interface TrackMapper {

    @Mapping(source = "sensor.id", target = "sensorId")
    TrackDTO toDto(Track track);
}
