package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.mapper.TrackMapper;
import com.lixegas.co2_monitor.model.Sensor;
import com.lixegas.co2_monitor.model.Track;
import com.lixegas.co2_monitor.model.dto.TrackDTO;
import com.lixegas.co2_monitor.model.request.TrackCreationRequest;
import com.lixegas.co2_monitor.repository.SensorRepository;
import com.lixegas.co2_monitor.repository.TrackRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final SensorRepository sensorRepository;
    private final TrackMapper trackMapper;

    private static final Logger logger = LoggerFactory.getLogger(TrackService.class);

    public List<TrackDTO> findAll() {
        logger.info("Fetching all tracks.");
        try {
            List<TrackDTO> tracks = trackRepository.findAll().stream()
                    .map(trackMapper::toDto)
                    .collect(Collectors.toList());
            logger.info("Found {} tracks.", tracks.size());
            return tracks;
        } catch (Exception e) {
            logger.error("Error while fetching tracks: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public TrackDTO findById(Long id) {
        logger.info("Fetching track with id {}", id);
        return trackRepository.findById(id)
                .map(trackMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("Track with id {} not found.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    public TrackDTO save(TrackCreationRequest trackCreationRequest) {
        logger.info("Saving new track for sensor id {}", trackCreationRequest.getSensorId());

        Sensor sensor = sensorRepository.findById(trackCreationRequest.getSensorId())
                .orElseThrow(() -> {
                    logger.warn("Sensor with id {} not found for track creation.", trackCreationRequest.getSensorId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        Track track = new Track();
        track.setCo2Level(trackCreationRequest.getCo2Level());
        track.setCreatedAt(Instant.now());
        track.setSensor(sensor);

        Track savedTrack = trackRepository.save(track);
        logger.info("Track with id {} and CO2 level {} saved successfully.", savedTrack.getId(), savedTrack.getCo2Level());

        return trackMapper.toDto(savedTrack);
    }

    public void delete(Long id) {
        logger.info("Attempting to delete track with id {}", id);

        if (!trackRepository.existsById(id)) {
            logger.warn("Track with id {} not found for deletion.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        trackRepository.deleteById(id);
        logger.info("Track with id {} deleted successfully.", id);
    }

    public List<TrackDTO> findTracksBySensorId(Long sensorId) {
        logger.info("Fetching tracks for sensor with id {}", sensorId);
        List<Track> tracks = trackRepository.findBySensorId(sensorId);

        if (tracks.isEmpty()) {
            logger.warn("No tracks found for sensor with id {}", sensorId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        logger.info("Found {} tracks for sensor with id {}", tracks.size(), sensorId);

        return tracks.stream()
                .map(trackMapper::toDto)
                .collect(Collectors.toList());
    }
}