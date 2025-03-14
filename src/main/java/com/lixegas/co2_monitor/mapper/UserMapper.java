package com.lixegas.co2_monitor.mapper;

import com.lixegas.co2_monitor.model.User;
import com.lixegas.co2_monitor.model.dto.UserDTO;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("userMapper")
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "role")
    UserDTO toDto(User user);

    @Mapping(source = "role", target = "role")
    User toEntity(UserDTO userDTO);
}
