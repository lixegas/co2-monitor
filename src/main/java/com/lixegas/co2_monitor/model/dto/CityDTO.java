package com.lixegas.co2_monitor.model.dto;

import com.lixegas.co2_monitor.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private Long id;
    private String name;
    private Instant createAt;
    private Instant updatedAt;
    private User user;
}