package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.dto.SensorDTO;
import com.lixegas.co2_monitor.model.Sensor;
import com.lixegas.co2_monitor.model.request.SensorCreationRequest;
import com.lixegas.co2_monitor.repository.SensorRepository;
import com.lixegas.co2_monitor.repository.DistrictRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final DistrictRepository districtRepository;

    public List<SensorDTO> findAll() {
        return sensorRepository.findAll().stream()
                .map(sensor -> new SensorDTO(
                        sensor.getId(),
                        sensor.getName(),
                        sensor.getCreatedAt(),
                        sensor.getUpdatedAt(),
                        sensor.getDistrict().getId()))
                .collect(Collectors.toList());
    }

    public Optional<SensorDTO> findById(Long id) {
        return sensorRepository.findById(id)
                .map(sensor -> new SensorDTO(
                        sensor.getId(),
                        sensor.getName(),
                        sensor.getCreatedAt(),
                        sensor.getUpdatedAt(),
                        sensor.getDistrict().getId()));
    }

    public SensorDTO save(SensorCreationRequest sensorCreationRequest) {
        Sensor sensor = new Sensor();

        var district = districtRepository.findById(sensorCreationRequest.getDistrictId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "District not found"));

        sensor.setName(sensorCreationRequest.getName());
        sensor.setCreatedAt(Instant.now());
        sensor.setUpdatedAt(null);
        sensor.setDistrict(district);

        Sensor savedSensor = sensorRepository.save(sensor);

        return new SensorDTO(
                savedSensor.getId(),
                savedSensor.getName(),
                savedSensor.getCreatedAt(),
                savedSensor.getUpdatedAt(),
                savedSensor.getDistrict().getId());
    }

    public SensorDTO update(Long id, SensorDTO sensorDTO) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));

        sensor.setName(sensorDTO.getName());
        sensor.setUpdatedAt(Instant.now());

        Sensor updatedSensor = sensorRepository.save(sensor);

        return new SensorDTO(
                updatedSensor.getId(),
                updatedSensor.getName(),
                updatedSensor.getCreatedAt(),
                updatedSensor.getUpdatedAt(),
                updatedSensor.getDistrict().getId());
    }

    public void delete(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found");
        }
        sensorRepository.deleteById(id);
    }
}
