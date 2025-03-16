package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.SensorDTO;
import com.lixegas.co2_monitor.model.request.SensorCreationRequest;
import com.lixegas.co2_monitor.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sensor")
@AllArgsConstructor
@Tag(name = "Sensor API", description = "Sensor management")
public class SensorController {

    private final SensorService sensorService;

    @GetMapping("/")
    @Operation(summary = "List of sensors")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SensorDTO.class))))
    @ApiResponse(responseCode = "500", description = "Error retrieving sensors",
            content = @Content(schema = @Schema(example = "{\"error\": \"Error retrieving sensors\"}")))
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        return ResponseEntity.ok(sensorService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sensor details")
    @ApiResponse(responseCode = "200", description = "Sensor found",
            content = @Content(schema = @Schema(implementation = SensorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Sensor not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"Sensor not found\"}")))
    public ResponseEntity<SensorDTO> getSensorById(@Parameter(description = "Sensor ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(sensorService.findById(id));
    }

    @PostMapping("/")
    @Operation(summary = "Create a new sensor")
    @ApiResponse(responseCode = "201", description = "Sensor created",
            content = @Content(schema = @Schema(implementation = SensorCreationRequest.class)))
    @ApiResponse(responseCode = "409", description = "Invalid data (sensor already exists)",
            content = @Content(schema = @Schema(example = "{\"error\": \"This sensor already exists\"}")))
    @ApiResponse(responseCode = "404", description = "District not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"District not found\"}")))
    public ResponseEntity<SensorDTO> createSensor(@Valid @RequestBody SensorCreationRequest sensorCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorService.save(sensorCreationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing sensor")
    @ApiResponse(responseCode = "200", description = "Sensor updated",
            content = @Content(schema = @Schema(implementation = SensorCreationRequest.class)))
    @ApiResponse(responseCode = "404", description = "Sensor not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"Sensor not found\"}")))
    public ResponseEntity<SensorDTO> updateSensor(@Parameter(description = "Sensor ID", example = "1") @PathVariable Long id, @Valid @RequestBody SensorDTO sensorDTO) {
        return ResponseEntity.ok(sensorService.update(id, sensorDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sensor")
    @ApiResponse(responseCode = "204", description = "Sensor deleted")
    @ApiResponse(responseCode = "404", description = "Sensor not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"Sensor not found\"}")))
    public ResponseEntity<Void> deleteSensor(@Parameter(description = "Sensor ID", example = "1") @PathVariable Long id) {
        sensorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
