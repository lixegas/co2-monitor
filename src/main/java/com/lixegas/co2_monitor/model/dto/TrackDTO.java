package com.lixegas.co2_monitor.model.dto;


import com.lixegas.co2_monitor.model.Sensor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {
    private Long id;
    private Double co2Level;
    private Instant createdAt;
    private Long sensorId;
}
