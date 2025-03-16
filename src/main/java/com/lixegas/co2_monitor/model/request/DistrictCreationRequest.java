package com.lixegas.co2_monitor.model.request;


import lombok.Data;

@Data
public class DistrictCreationRequest {
    private String name;
    private Long cityId;
}
