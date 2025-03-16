package com.lixegas.co2_monitor.model.dto;

import com.lixegas.co2_monitor.model.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDTO {
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private Long cityId;
}
