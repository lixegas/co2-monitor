package com.lixegas.co2_monitor.model.dto;

import com.lixegas.co2_monitor.model.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String pass;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;
}
