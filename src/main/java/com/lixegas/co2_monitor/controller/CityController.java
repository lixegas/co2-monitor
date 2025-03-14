package com.lixegas.co2_monitor.controller;

import com.lixegas.co2_monitor.model.dto.CityDTO;
import com.lixegas.co2_monitor.model.request.CityCreationRequest;
import com.lixegas.co2_monitor.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/city")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/")
    public ResponseEntity<List<CityDTO>> getAllCities() {
            List<CityDTO> cities = cityService.findAll();
            return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) {
        CityDTO cityDTO = cityService.findById(id);
        return ResponseEntity.ok(cityDTO);
    }

    @PostMapping("/")
    public ResponseEntity<CityDTO> createCity(@RequestBody CityCreationRequest cityCreationRequest) {
        CityDTO cityDTO = cityService.save(cityCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        CityDTO updatedCity = cityService.update(id, cityDTO);
        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
