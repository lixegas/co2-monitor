package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.TrackDTO;
import com.lixegas.co2_monitor.model.request.TrackCreationRequest;
import com.lixegas.co2_monitor.service.TrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/track")
@AllArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/")
    @Operation(summary = "Get all tracks")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all tracks",
            content = @Content(schema = @Schema(implementation = TrackDTO.class)))
    @ApiResponse(responseCode = "500", description = "Error retrieving tracks",
            content = @Content(schema = @Schema(example = "{\"error\": \"Internal server error\"}")))
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        return ResponseEntity.ok(trackService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get track details by ID")
    @ApiResponse(responseCode = "200", description = "Track found",
            content = @Content(schema = @Schema(implementation = TrackDTO.class)))
    @ApiResponse(responseCode = "404", description = "Track not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"Track not found\"}")))
    public ResponseEntity<TrackDTO> getTrackById(
            @Parameter(description = "Track ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(trackService.findById(id));
    }

    @GetMapping("/sensor/{sensorId}")
    @Operation(summary = "Get tracks by sensor ID")
    @ApiResponse(responseCode = "200", description = "Tracks found",
            content = @Content(schema = @Schema(implementation = TrackDTO.class)))
    @ApiResponse(responseCode = "404", description = "No tracks found for the sensor",
            content = @Content(schema = @Schema(example = "{\"error\": \"Tracks not found for the sensor\"}")))
    public ResponseEntity<List<TrackDTO>> getTracksBySensorId(@PathVariable Long sensorId) {
        return ResponseEntity.ok(trackService.findTracksBySensorId(sensorId));
    }

    @PostMapping("/")
    @Operation(summary = "Create a new track")
    @ApiResponse(responseCode = "201", description = "Track created successfully",
            content = @Content(schema = @Schema(implementation = TrackDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid track creation request",
            content = @Content(schema = @Schema(example = "{\"error\": \"Invalid input\"}")))
    public ResponseEntity<TrackDTO> createTrack(@RequestBody TrackCreationRequest trackCreationRequest) {
        TrackDTO createdTrack = trackService.save(trackCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrack);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a track by ID")
    @ApiResponse(responseCode = "204", description = "Track deleted successfully")
    @ApiResponse(responseCode = "404", description = "Track not found",
            content = @Content(schema = @Schema(example = "{\"error\": \"Track not found\"}")))
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        trackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
