package com.lixegas.co2_monitor.repository;

import com.lixegas.co2_monitor.model.User;
import com.lixegas.co2_monitor.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
