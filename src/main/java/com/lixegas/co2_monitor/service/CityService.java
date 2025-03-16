package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.User;
import com.lixegas.co2_monitor.model.dto.CityDTO;
import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.request.CityCreationRequest;
import com.lixegas.co2_monitor.repository.CityRepository;
import com.lixegas.co2_monitor.repository.UserRepository;
import lombok.AllArgsConstructor;
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

    public List<CityDTO> findAll() {
        try {
            return cityRepository.findAll().stream()
                    .map(city -> new CityDTO(
                            city.getId(),
                            city.getName(),
                            city.getCreatedAt(),
                            city.getUpdatedAt()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CityDTO findById(Long id) {
        return cityRepository.findById(id)
                .map(city -> new CityDTO(
                        city.getId(),
                        city.getName(),
                        city.getCreatedAt(),
                        city.getUpdatedAt()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public CityDTO save(CityCreationRequest cityCreationRequest) {
        if (cityRepository.findByName(cityCreationRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        User user = userRepository.findById(cityCreationRequest.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        City city = new City();
        city.setName(cityCreationRequest.getName());
        city.setCreatedAt(Instant.now());
        city.setUser(user);

        City savedCity = cityRepository.save(city);

        return new CityDTO(
                savedCity.getId(),
                savedCity.getName(),
                savedCity.getCreatedAt(),
                savedCity.getUpdatedAt());
    }

    public CityDTO update(Long id, CityDTO cityDTO) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (cityRepository.findByName(cityDTO.getName()).isPresent() && !city.getName().equals(cityDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        city.setName(cityDTO.getName());
        city.setUpdatedAt(Instant.now());

        City updatedCity = cityRepository.save(city);

        return new CityDTO(
                updatedCity.getId(),
                updatedCity.getName(),
                updatedCity.getCreatedAt(),
                updatedCity.getUpdatedAt());
    }

    public void delete(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        cityRepository.deleteById(id);
    }
}
