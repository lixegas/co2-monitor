package com.lixegas.co2_monitor.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SensorDTO {

    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private Long districtId;
}
