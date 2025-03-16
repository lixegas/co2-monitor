package com.lixegas.co2_monitor.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CityCreationRequest {
    private String name;
    private Long userId;
}
