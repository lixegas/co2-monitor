package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.User;
import com.lixegas.co2_monitor.model.dto.UserDTO;
import com.lixegas.co2_monitor.model.enumeration.Role;
import com.lixegas.co2_monitor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDTO save(UserDTO userDTO){
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPass(userDTO.getPass());
        user.setRole(Role.ADMIN);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(null);

        User userSaved = userRepository.save(user);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(userSaved.getId());
        userDTO1.setUsername(userSaved.getUsername());
        userDTO1.setPass(userSaved.getPass());
        userDTO1.setRole(userSaved.getRole());
        userDTO1.setCreatedAt(userSaved.getCreatedAt());
        userDTO1.setUpdatedAt(userSaved.getUpdatedAt());

        return userDTO1;
    }
}
