package com.lixegas.co2_monitor.repository;

import com.lixegas.co2_monitor.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findBySensorId(Long sensorId);
}
