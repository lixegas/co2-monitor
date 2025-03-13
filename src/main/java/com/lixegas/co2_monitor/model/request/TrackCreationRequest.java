package com.lixegas.co2_monitor.model.request;

import lombok.Data;

@Data
public class TrackCreationRequest {

    private Double co2Level;
    private Long sensorId;
}
