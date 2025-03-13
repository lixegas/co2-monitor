package com.lixegas.co2_monitor.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {

    private Long id;
    private Double co2Level;
    private String createdAt;
    private Long sensorId;
}
