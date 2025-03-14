package com.lixegas.co2_monitor.repository;

import com.lixegas.co2_monitor.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByCityId(Long cityId);

    Optional<District> findByName(String name);
}
