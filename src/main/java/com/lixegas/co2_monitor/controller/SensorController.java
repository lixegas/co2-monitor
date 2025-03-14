package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.SensorDTO;
import com.lixegas.co2_monitor.model.request.SensorCreationRequest;
import com.lixegas.co2_monitor.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sensor")
@AllArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping("/")
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        return ResponseEntity.ok(sensorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable Long id) {
        SensorDTO sensorDTO = sensorService.findById(id);
        return ResponseEntity.ok(sensorDTO);
    }

    @PostMapping("/")
    public ResponseEntity<SensorDTO> createSensor(@RequestBody SensorCreationRequest sensorCreationRequest) {
        SensorDTO sensorDTO = sensorService.save(sensorCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable Long id, @RequestBody SensorDTO sensorDTO) {
        SensorDTO sensorDTO1 = sensorService.update(id,sensorDTO);
        return ResponseEntity.ok().body(sensorDTO1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
