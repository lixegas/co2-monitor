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
        DistrictDTO districtDTO = districtService.findById(id);
        return ResponseEntity.ok(districtDTO);
    }

    @PostMapping("/")
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictCreationRequest districtCreationRequest) {
        DistrictDTO districtDTO = districtService.save(districtCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(districtDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistrictDTO> updateDistrict(@PathVariable Long id, @RequestBody DistrictDTO districtDTO) {
        DistrictDTO updatedDistrict = districtService.update(id, districtDTO);
        return ResponseEntity.ok(updatedDistrict);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        districtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}