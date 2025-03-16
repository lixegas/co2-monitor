package com.lixegas.co2_monitor.service;

import com.lixegas.co2_monitor.mapper.DistrictMapper;
import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.dto.DistrictDTO;
import com.lixegas.co2_monitor.model.request.DistrictCreationRequest;
import com.lixegas.co2_monitor.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lixegas.co2_monitor.model.District;
import com.lixegas.co2_monitor.repository.DistrictRepository;
import org.springframework.http.HttpStatus;
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
    private final DistrictMapper districtMapper;

    private static final Logger logger = LoggerFactory.getLogger(DistrictService.class);

    public List<DistrictDTO> findAll() {
        logger.info("Fetching all districts.");
        try {
            List<DistrictDTO> districts = districtRepository.findAll().stream()
                    .map(districtMapper::toDto)
                    .collect(Collectors.toList());
            logger.info("Found {} districts.", districts.size());
            return districts;
        } catch (Exception exception) {
            logger.error("Error while fetching districts: {}", exception.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public DistrictDTO findById(Long id) {
        logger.info("Fetching district with id {}", id);
        return districtRepository.findById(id)
                .map(districtMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("District with id {} not found.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    public DistrictDTO save(DistrictCreationRequest districtCreationRequest) {
        logger.info("Saving new district with name {}", districtCreationRequest.getName());

        if (districtRepository.findByName(districtCreationRequest.getName()).isPresent()) {
            logger.warn("District with name {} already exists.", districtCreationRequest.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        City city = cityRepository.findById(districtCreationRequest.getCityId())
                .orElseThrow(() -> {
                    logger.warn("City with id {} not found.", districtCreationRequest.getCityId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        District district = new District();
        district.setName(districtCreationRequest.getName());
        district.setCreatedAt(Instant.now());
        district.setCity(city);

        District savedDistrict = districtRepository.save(district);
        logger.info("District with id {} and name {} saved successfully.", savedDistrict.getId(), savedDistrict.getName());

        return districtMapper.toDto(savedDistrict);
    }

    public DistrictDTO update(Long id, DistrictDTO districtDTO) {
        logger.info("Updating district with id {}", id);

        District district = districtRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("District with id {} not found for update.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        if (districtRepository.findByName(districtDTO.getName()).isPresent() && !district.getName().equals(districtDTO.getName())) {
            logger.warn("District name {} already exists, cannot update.", districtDTO.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        district.setName(districtDTO.getName());
        district.setUpdatedAt(Instant.now());

        District updatedDistrict = districtRepository.save(district);
        logger.info("District with id {} updated successfully.", updatedDistrict.getId());

        return districtMapper.toDto(updatedDistrict);
    }

    public void delete(Long id) {
        logger.info("Attempting to delete district with id {}", id);

        if (!districtRepository.existsById(id)) {
            logger.warn("District with id {} not found for deletion.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        districtRepository.deleteById(id);
        logger.info("District with id {} deleted successfully.", id);
    }
}
