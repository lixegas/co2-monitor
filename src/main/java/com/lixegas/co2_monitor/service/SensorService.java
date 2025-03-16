package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.mapper.SensorMapper;
import com.lixegas.co2_monitor.model.District;
import com.lixegas.co2_monitor.model.dto.SensorDTO;
import com.lixegas.co2_monitor.model.Sensor;
import com.lixegas.co2_monitor.model.request.SensorCreationRequest;
import com.lixegas.co2_monitor.repository.SensorRepository;
import com.lixegas.co2_monitor.repository.DistrictRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final SensorMapper sensorMapper;

    private static final Logger logger = LoggerFactory.getLogger(SensorService.class);

    public List<SensorDTO> findAll() {
        logger.info("Fetching all sensors.");
        try {
            List<SensorDTO> sensors = sensorRepository.findAll().stream()
                    .map(sensorMapper::toDto)
                    .collect(Collectors.toList());
            logger.info("Found {} sensors.", sensors.size());
            return sensors;
        } catch (Exception e) {
            logger.error("Error while fetching sensors: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SensorDTO findById(Long id) {
        logger.info("Fetching sensor with id {}", id);
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Sensor with id {} not found.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
        return sensorMapper.toDto(sensor);
    }

    public SensorDTO save(SensorCreationRequest sensorCreationRequest) {
        logger.info("Saving new sensor with name {}", sensorCreationRequest.getName());

        if (sensorRepository.findByName(sensorCreationRequest.getName()).isPresent()) {
            logger.warn("Sensor with name {} already exists.", sensorCreationRequest.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        District district = districtRepository.findById(sensorCreationRequest.getDistrictId())
                .orElseThrow(() -> {
                    logger.warn("District with id {} not found.", sensorCreationRequest.getDistrictId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        Sensor sensor = new Sensor();
        sensor.setName(sensorCreationRequest.getName());
        sensor.setCreatedAt(Instant.now());
        sensor.setDistrict(district);

        Sensor savedSensor = sensorRepository.save(sensor);
        logger.info("Sensor with id {} and name {} saved successfully.", savedSensor.getId(), savedSensor.getName());

        return sensorMapper.toDto(savedSensor);
    }

    public SensorDTO update(Long id, SensorDTO sensorDTO) {
        logger.info("Updating sensor with id {}", id);

        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Sensor with id {} not found for update.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        if (sensorRepository.findByName(sensorDTO.getName()).isPresent()) {
            logger.warn("Sensor with name {} already exists.", sensorDTO.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        sensor.setName(sensorDTO.getName());
        sensor.setUpdatedAt(Instant.now());

        Sensor updatedSensor = sensorRepository.save(sensor);
        logger.info("Sensor with id {} updated successfully.", updatedSensor.getId());

        return sensorMapper.toDto(updatedSensor);
    }

    public void delete(Long id) {
        logger.info("Attempting to delete sensor with id {}", id);

        if (!sensorRepository.existsById(id)) {
            logger.warn("Sensor with id {} not found for deletion.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        sensorRepository.deleteById(id);
        logger.info("Sensor with id {} deleted successfully.", id);
    }
}
