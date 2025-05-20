// File: src/main/java/com/logistics/dto/VehicleDto.java
package com.logistics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleDto {
    @NotBlank(message = "Registration number is required")
    private String registrationNumber;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    @NotBlank(message = "Type is required")
    private String type;
    
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Double capacityKg;
    
    private Double fuelCapacityLiters;
    
    private String status;
    
    private LocalDateTime lastMaintenanceDate;
    
    private Double latitude;
    
    private Double longitude;
}