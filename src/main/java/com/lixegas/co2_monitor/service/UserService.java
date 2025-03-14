package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.mapper.UserMapper;
import com.lixegas.co2_monitor.model.User;
import com.lixegas.co2_monitor.model.dto.UserDTO;
import com.lixegas.co2_monitor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO save(UserDTO userDTO){
        userDTO.setCreatedAt(Instant.now());
        userDTO.setUpdatedAt(null);

        User user = userMapper.toEntity(userDTO);

        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
