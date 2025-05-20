// File: src/main/java/com/logistics/service/VehicleService.java
package com.logistics.service;

import com.logistics.dto.VehicleDto;
import com.logistics.dto.VehicleLocationUpdateDto;
import com.logistics.model.Vehicle;
import com.logistics.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    
    public Optional<Vehicle> getVehicleById(UUID id) {
        return vehicleRepository.findById(id);
    }
    
    @Transactional
    public Vehicle createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        updateVehicleFromDto(vehicle, vehicleDto);
        return vehicleRepository.save(vehicle);
    }
    
    @Transactional
    public Optional<Vehicle> updateVehicle(UUID id, VehicleDto vehicleDto) {
        return vehicleRepository.findById(id)
                .map(existingVehicle -> {
                    updateVehicleFromDto(existingVehicle, vehicleDto);
                    return vehicleRepository.save(existingVehicle);
                });
    }
    
    @Transactional
    public boolean deleteVehicle(UUID id) {
        return vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicleRepository.delete(vehicle);
                    return true;
                })
                .orElse(false);
    }
    
    @Transactional
    public Optional<Vehicle> updateVehicleLocation(UUID id, VehicleLocationUpdateDto locationUpdateDto) {
        return vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle.setLatitude(locationUpdateDto.getLatitude());
                    vehicle.setLongitude(locationUpdateDto.getLongitude());
                    vehicle.setLastLocationUpdate(LocalDateTime.now());
                    return vehicleRepository.save(vehicle);
                });
    }
    
    private void updateVehicleFromDto(Vehicle vehicle, VehicleDto vehicleDto) {
        vehicle.setRegistrationNumber(vehicleDto.getRegistrationNumber());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setType(vehicleDto.getType());
        vehicle.setCapacityKg(vehicleDto.getCapacityKg());
        vehicle.setFuelCapacityLiters(vehicleDto.getFuelCapacityLiters());
        vehicle.setStatus(vehicleDto.getStatus());
        vehicle.setLastMaintenanceDate(vehicleDto.getLastMaintenanceDate());
        vehicle.setLatitude(vehicleDto.getLatitude());
        vehicle.setLongitude(vehicleDto.getLongitude());
        
        if (vehicle.getLastLocationUpdate() == null && 
                vehicleDto.getLatitude() != null && 
                vehicleDto.getLongitude() != null) {
            vehicle.setLastLocationUpdate(LocalDateTime.now());
        }
    }
}