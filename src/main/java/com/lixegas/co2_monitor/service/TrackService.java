package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.dto.TrackDTO;
import com.lixegas.co2_monitor.model.Track;
import com.lixegas.co2_monitor.model.request.TrackCreationRequest;
import com.lixegas.co2_monitor.repository.TrackRepository;
import com.lixegas.co2_monitor.repository.SensorRepository;
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
public class TrackService {

    private final TrackRepository trackRepository;
    private final SensorRepository sensorRepository;

    public List<TrackDTO> findAll() {
        return trackRepository.findAll().stream()
                .map(track -> new TrackDTO(
                        track.getId(),
                        track.getCo2Level(),
                        track.getCreatedAt(),
                        track.getSensor().getId()))
                .collect(Collectors.toList());
    }

    public Optional<TrackDTO> findById(Long id) {
        return trackRepository.findById(id)
                .map(track -> new TrackDTO(
                        track.getId(),
                        track.getCo2Level(),
                        track.getCreatedAt(),
                        track.getSensor().getId()));
    }

    public TrackDTO save(TrackCreationRequest trackCreationRequest) {
        Track track = new Track();

        var sensor = sensorRepository.findById(trackCreationRequest.getSensorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));

        track.setCo2Level(trackCreationRequest.getCo2Level());
        track.setCreatedAt(Instant.now().toString());
        track.setSensor(sensor);

        Track savedTrack = trackRepository.save(track);

        return new TrackDTO(
                savedTrack.getId(),
                savedTrack.getCo2Level(),
                savedTrack.getCreatedAt(),
                savedTrack.getSensor().getId());
    }


    public void delete(Long id) {
        if (!trackRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not found");
        }
        trackRepository.deleteById(id);
    }


    public List<TrackDTO> findTracksBySensorId(Long sensorId) {
        List<Track> tracks = trackRepository.findBySensorId(sensorId);

        return tracks.stream()
                .map(track -> {
                    TrackDTO trackDTO = new TrackDTO();
                    trackDTO.setId(track.getId());
                    trackDTO.setCo2Level(track.getCo2Level());
                    trackDTO.setCreatedAt(track.getCreatedAt());
                    trackDTO.setSensorId(track.getSensor().getId());
                    return trackDTO;
                })
                .collect(Collectors.toList());
    }
}
