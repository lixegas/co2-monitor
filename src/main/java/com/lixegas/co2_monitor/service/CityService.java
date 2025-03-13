package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.dto.CityDTO;
import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.request.CityCreationRequest;
import com.lixegas.co2_monitor.repository.CityRepository;
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

    public List<CityDTO> findAll() {
        return cityRepository.findAll().stream()
                .map(city -> new CityDTO(
                        city.getId(),
                        city.getName(),
                        city.getCreatedAt(),
                        city.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    public CityDTO findById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        return new CityDTO(
                city.getId(),
                city.getName(),
                city.getCreatedAt(),
                city.getUpdatedAt());
    }

    public CityDTO save(CityCreationRequest cityCreationRequest) {
        City city = new City();
        city.setName(cityCreationRequest.getName());
        city.setCreatedAt(Instant.now());
        city.setUpdatedAt(null);

        City savedCity = cityRepository.save(city);

        return new CityDTO(
                savedCity.getId(),
                savedCity.getName(),
                savedCity.getCreatedAt(),
                savedCity.getUpdatedAt());
    }

    public CityDTO update(Long id, CityDTO cityDTO) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

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
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        cityRepository.deleteById(id);
    }
}
