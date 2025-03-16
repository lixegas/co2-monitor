package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.CityDTO;
import com.lixegas.co2_monitor.model.request.CityCreationRequest;
import com.lixegas.co2_monitor.service.CityService;
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
@RequestMapping("api/v1/city")
@AllArgsConstructor
@Tag(name = "City API", description = "City management")
public class CityController {

    private final CityService cityService;

    @GetMapping("/")
    @Operation(summary = "List of cities")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CityDTO.class))))
    @ApiResponse(responseCode = "500", description = "Error retrieving cities",
            content = @Content(schema = @Schema(example = "{\"error\": \"Error retrieving cities\"}")))
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "City details")
    @ApiResponse(responseCode = "200", description = "City found",
            content = @Content(schema = @Schema(implementation = CityDTO.class)))
    @ApiResponse(responseCode = "404", description = "City not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"City with ID 1 not found\"}")))
    public ResponseEntity<CityDTO> getCityById(@Parameter(description = "City ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PostMapping("/")
    @Operation(summary = "Create a new city")
    @ApiResponse(responseCode = "201", description = "City created",
            content = @Content(schema = @Schema(implementation = CityDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid data (user not found)",
            content = @Content(schema = @Schema(example = "{\"error\": \"User with ID 5 not found\"}")))
    @ApiResponse(responseCode = "409", description = "City already exists",
            content = @Content(schema = @Schema(example = "{\"error\": \"City 'Rome' already exists\"}")))
    public ResponseEntity<CityDTO> createCity(@Valid @RequestBody CityCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a city")
    @ApiResponse(responseCode = "200", description = "City updated",
            content = @Content(schema = @Schema(implementation = CityCreationRequest.class)))
    @ApiResponse(responseCode = "404", description = "City not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"City with ID 1 not found\"}")))
    @ApiResponse(responseCode = "409", description = "City name already exists",
            content = @Content(schema = @Schema(example = "{\"error\": \"City with name 'Milan' already exists\"}")))
    public ResponseEntity<CityDTO> updateCity(@Parameter(description = "City ID", example = "1") @PathVariable Long id, @Valid @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.update(id, cityDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a city")
    @ApiResponse(responseCode = "204", description = "City deleted")
    @ApiResponse(responseCode = "404", description = "City not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"City with ID 1 not found\"}")))
    public ResponseEntity<Void> deleteCity(@Parameter(description = "City ID", example = "1") @PathVariable Long id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
