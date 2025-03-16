package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.mapper.CityMapper;
import com.lixegas.co2_monitor.model.User;
import com.lixegas.co2_monitor.model.dto.CityDTO;
import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.request.CityCreationRequest;
import com.lixegas.co2_monitor.repository.CityRepository;
import com.lixegas.co2_monitor.repository.UserRepository;
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
public class CityService {

    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final CityMapper cityMapper;

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    public List<CityDTO> findAll() {
        logger.info("Fetching all cities.");
        try {
            List<CityDTO> cities = cityRepository.findAll().stream()
                    .map(cityMapper::toDto)
                    .collect(Collectors.toList());
            logger.info("Found {} cities.", cities.size());
            return cities;
        } catch (Exception e) {
            logger.error("Error while fetching cities: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CityDTO findById(Long id) {
        logger.info("Fetching city with id {}", id);
        return cityRepository.findById(id)
                .map(cityMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("City with id {} not found.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    public CityDTO save(CityCreationRequest cityCreationRequest) {
        logger.info("Saving new city with name {}", cityCreationRequest.getName());

        if (cityRepository.findByName(cityCreationRequest.getName()).isPresent()) {
            logger.warn("City with name {} already exists.", cityCreationRequest.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        User user = userRepository.findById(cityCreationRequest.getUserId())
                .orElseThrow(() -> {
                    logger.warn("User with id {} not found.", cityCreationRequest.getUserId());
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST);
                });

        City city = new City();
        city.setName(cityCreationRequest.getName());
        city.setCreatedAt(Instant.now());
        city.setUser(user);

        City savedCity = cityRepository.save(city);
        logger.info("City with id {} and name {} saved successfully.", savedCity.getId(), savedCity.getName());

        return cityMapper.toDto(savedCity);
    }

    public CityDTO update(Long id, CityDTO cityDTO) {
        logger.info("Updating city with id {}", id);

        City city = cityRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("City with id {} not found for update.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        if (cityRepository.findByName(cityDTO.getName()).isPresent() && !city.getName().equals(cityDTO.getName())) {
            logger.warn("City name {} already exists, cannot update.", cityDTO.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        city.setName(cityDTO.getName());
        city.setUpdatedAt(Instant.now());

        City updatedCity = cityRepository.save(city);
        logger.info("City with id {} updated successfully.", updatedCity.getId());

        return cityMapper.toDto(updatedCity);
    }

    public void delete(Long id) {
        logger.info("Attempting to delete city with id {}", id);

        if (!cityRepository.existsById(id)) {
            logger.warn("City with id {} not found for deletion.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        cityRepository.deleteById(id);
        logger.info("City with id {} deleted successfully.", id);
    }
}
