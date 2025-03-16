package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.UserDTO;
import com.lixegas.co2_monitor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO userDTO1 = userService.save(userDTO);
        return ResponseEntity.ok(userDTO1);
    }
}
