// File: src/main/java/com/logistics/controller/VehicleController.java
package com.logistics.controller;

import com.logistics.dto.VehicleDto;
import com.logistics.dto.VehicleLocationUpdateDto;
import com.logistics.model.Vehicle;
import com.logistics.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    
    private final VehicleService vehicleService;
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable UUID id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        return new ResponseEntity<>(vehicleService.createVehicle(vehicleDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable UUID id, @Valid @RequestBody VehicleDto vehicleDto) {
        return vehicleService.updateVehicle(id, vehicleDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        if (vehicleService.deleteVehicle(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PatchMapping("/{id}/location")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Vehicle> updateVehicleLocation(
            @PathVariable UUID id,
            @Valid @RequestBody VehicleLocationUpdateDto locationUpdateDto) {
        return vehicleService.updateVehicleLocation(id, locationUpdateDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}