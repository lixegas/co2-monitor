package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.dto.DistrictDTO;
import com.lixegas.co2_monitor.model.request.DistrictCreationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.lixegas.co2_monitor.model.District;
import com.lixegas.co2_monitor.repository.DistrictRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public List<DistrictDTO> findAll() {
        return districtRepository.findAll().stream()
                .map(district -> new DistrictDTO(
                        district.getId(),
                        district.getName(),
                        district.getCreatedAt(),
                        district.getUpdatedAt(),
                        district.getCity().getId()))
                .collect(Collectors.toList());
    }

    public DistrictDTO findById(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "District not found"));

        return new DistrictDTO(
                district.getId(),
                district.getName(),
                district.getCreatedAt(),
                district.getUpdatedAt(),
                district.getCity().getId());
    }

    public List<DistrictDTO> findByCityId(Long cityId) {
        return districtRepository.findByCityId(cityId).stream()
                .map(district -> new DistrictDTO(
                        district.getId(),
                        district.getName(),
                        district.getCreatedAt(),
                        district.getUpdatedAt(),
                        district.getCity().getId()))
                .collect(Collectors.toList());
    }

    public DistrictDTO save(DistrictCreationRequest districtCreationRequest) {
        District district = new District();
        district.setName(districtCreationRequest.getName());
        district.setCreatedAt(Instant.now());
        district.setUpdatedAt(null);


        City city = new City();
        city.setId(districtCreationRequest.getCityId());
        district.setCity(city);

        District savedDistrict = districtRepository.save(district);

        return new DistrictDTO(
                savedDistrict.getId(),
                savedDistrict.getName(),
                savedDistrict.getCreatedAt(),
                savedDistrict.getUpdatedAt(),
                savedDistrict.getCity().getId());
    }

    public DistrictDTO update(Long id, DistrictDTO districtDTO) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "District not found"));

        district.setName(districtDTO.getName());
        district.setUpdatedAt(Instant.now());
        City city = new City();
        city.setId(districtDTO.getCityId());
        district.setCity(city);

        District updatedDistrict = districtRepository.save(district);

        return new DistrictDTO(
                updatedDistrict.getId(),
                updatedDistrict.getName(),
                updatedDistrict.getCreatedAt(),
                updatedDistrict.getUpdatedAt(),
                updatedDistrict.getCity().getId());
    }

    public void delete(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "District not found"));

        districtRepository.deleteById(id);
    }
}
