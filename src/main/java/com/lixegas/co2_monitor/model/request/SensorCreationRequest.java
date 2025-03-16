package com.lixegas.co2_monitor.model.request;

import lombok.Data;

@Data
public class SensorCreationRequest {
    private String name;
    private Long districtId;
}
