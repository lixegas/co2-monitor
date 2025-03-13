package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.TrackDTO;
import com.lixegas.co2_monitor.model.request.TrackCreationRequest;
import com.lixegas.co2_monitor.service.TrackService;
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
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        return ResponseEntity.ok(trackService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDTO> getTrackById(@PathVariable Long id) {
        return trackService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<List<TrackDTO>> getTracksBySensorId(@PathVariable Long sensorId) {
        List<TrackDTO> trackDTOs = trackService.findTracksBySensorId(sensorId);

        if (trackDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(trackDTOs);
    }

    @PostMapping("/")
    public void createTrack(@RequestBody TrackCreationRequest trackCreationRequest) {
        ResponseEntity.status(HttpStatus.CREATED).body(trackService.save(trackCreationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        if (trackService.findById(id).isPresent()) {
            trackService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
