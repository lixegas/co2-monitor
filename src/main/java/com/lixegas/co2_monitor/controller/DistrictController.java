package com.lixegas.co2_monitor.controller;


import com.lixegas.co2_monitor.model.dto.DistrictDTO;
import com.lixegas.co2_monitor.model.request.DistrictCreationRequest;
import com.lixegas.co2_monitor.service.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/district")
@AllArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("/")
    public ResponseEntity<List<DistrictDTO>> getAllDistricts() {
        List<DistrictDTO> districts = districtService.findAll();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistrictDTO> getDistrictById(@PathVariable Long id) {
        try {
            DistrictDTO districtDTO = districtService.findById(id);
            return ResponseEntity.ok(districtDTO);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictCreationRequest districtCreationRequest) {
        DistrictDTO districtDTO = districtService.save(districtCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(districtDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistrictDTO> updateDistrict(@PathVariable Long id, @RequestBody DistrictDTO districtDTO) {
        try {
            DistrictDTO updatedDistrict = districtService.update(id, districtDTO);
            return ResponseEntity.ok(updatedDistrict);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        try {
            districtService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build();
        }
    }
}