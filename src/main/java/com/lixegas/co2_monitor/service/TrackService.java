package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.Sensor;
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
        try {
            return trackRepository.findAll().stream()
                    .map(track -> new TrackDTO(
                            track.getId(),
                            track.getCo2Level(),
                            track.getCreatedAt(),
                            track.getSensor().getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public TrackDTO findById(Long id) {
        return trackRepository.findById(id)
                .map(track -> new TrackDTO(
                        track.getId(),
                        track.getCo2Level(),
                        track.getCreatedAt(),
                        track.getSensor().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public TrackDTO save(TrackCreationRequest trackCreationRequest) {
        Track track = new Track();

        Sensor sensor = sensorRepository.findById(trackCreationRequest.getSensorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        track.setCo2Level(trackCreationRequest.getCo2Level());
        track.setCreatedAt(Instant.now());
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        trackRepository.deleteById(id);
    }

    public List<TrackDTO> findTracksBySensorId(Long sensorId) {
        List<Track> tracks = trackRepository.findBySensorId(sensorId);

        if (tracks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return tracks.stream()
                .map(track -> new TrackDTO(
                        track.getId(),
                        track.getCo2Level(),
                        track.getCreatedAt(),
                        track.getSensor().getId()))
                .collect(Collectors.toList());
    }
}
