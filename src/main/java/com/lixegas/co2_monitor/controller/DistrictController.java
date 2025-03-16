package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.DistrictDTO;
import com.lixegas.co2_monitor.model.request.DistrictCreationRequest;
import com.lixegas.co2_monitor.service.DistrictService;
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
@RequestMapping("api/v1/district")
@AllArgsConstructor
@Tag(name = "District API", description = "District management operations")
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("/")
    @Operation(summary = "Retrieve all districts")
    @ApiResponse(responseCode = "200", description = "List of districts retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DistrictDTO.class))))
    @ApiResponse(responseCode = "500", description = "Error while retrieving districts",
            content = @Content(schema = @Schema(example = "{\"error\": \"Error while retrieving districts\"}")))
    public ResponseEntity<List<DistrictDTO>> getAllDistricts() {
        List<DistrictDTO> districts = districtService.findAll();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a district by ID")
    @ApiResponse(responseCode = "200", description = "District found",
            content = @Content(schema = @Schema(implementation = DistrictDTO.class)))
    @ApiResponse(responseCode = "404", description = "District not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"District with ID 1 not found\"}")))
    public ResponseEntity<DistrictDTO> getDistrictById(@Parameter(description = "ID of the district", example = "1") @PathVariable Long id) {
        DistrictDTO district = districtService.findById(id);
        return ResponseEntity.ok(district);
    }

    @PostMapping("/")
    @Operation(summary = "Create a new district")
    @ApiResponse(responseCode = "201", description = "District created successfully",
            content = @Content(schema = @Schema(implementation = DistrictCreationRequest.class)))
    @ApiResponse(responseCode = "400", description = "Invalid data or district already exists",
            content = @Content(schema = @Schema(example = "{\"error\": \"A district with the name 'districtName' already exists.\"}")))
    @ApiResponse(responseCode = "404", description = "City not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"City with ID X not found.\"}")))
    public ResponseEntity<DistrictDTO> createDistrict(@Valid @RequestBody DistrictCreationRequest districtCreationRequest) {
        DistrictDTO district = districtService.save(districtCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(district);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing district")
    @ApiResponse(responseCode = "200", description = "District updated successfully",
            content = @Content(schema = @Schema(implementation = DistrictCreationRequest.class)))
    @ApiResponse(responseCode = "404", description = "District not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"District with ID 1 not found\"}")))
    @ApiResponse(responseCode = "409", description = "District with name already exists",
            content = @Content(schema = @Schema(example = "{\"error\": \"A district with the name 'districtName' already exists.\"}")))
    public ResponseEntity<DistrictDTO> updateDistrict(@Parameter(description = "ID of the district", example = "1") @PathVariable Long id, @RequestBody DistrictDTO districtDTO) {
        DistrictDTO updatedDistrict = districtService.update(id, districtDTO);
        return ResponseEntity.ok(updatedDistrict);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a district")
    @ApiResponse(responseCode = "204", description = "District deleted successfully")
    @ApiResponse(responseCode = "404", description = "District not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"District with ID 1 not found\"}")))
    public ResponseEntity<Void> deleteDistrict(@Parameter(description = "ID of the district", example = "1") @PathVariable Long id) {
        districtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
