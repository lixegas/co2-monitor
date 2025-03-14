package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.dto.DistrictDTO;
import com.lixegas.co2_monitor.model.request.DistrictCreationRequest;
import com.lixegas.co2_monitor.repository.CityRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;

    public List<DistrictDTO> findAll() {
        try {
            return districtRepository.findAll().stream()
                    .map(district -> new DistrictDTO(
                            district.getId(),
                            district.getName(),
                            district.getCreatedAt(),
                            district.getUpdatedAt(),
                            district.getCity().getId()))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante il recupero");
        }
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

    public DistrictDTO save(DistrictCreationRequest districtCreationRequest) {
        District district = new District();

        Optional<District> districtOptional = districtRepository.findByName(districtCreationRequest.getName());
        if(districtOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questo distretto esiste già");
        } else {
            district.setName(districtCreationRequest.getName());
        }

        district.setCreatedAt(Instant.now());
        district.setUpdatedAt(null);


        Optional<City> cityOptional = cityRepository.findById(districtCreationRequest.getCityId());
        if (cityOptional.isPresent()){
            City city = new City();
            city.setId(districtCreationRequest.getCityId());
            district.setCity(city);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Questa città non esiste");
        }

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

        Optional<City> optionalCity = cityRepository.findById(districtDTO.getCityId());
        optionalCity.ifPresent(district::setCity);

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
