// File: src/main/java/com/logistics/dto/VehicleLocationUpdateDto.java
package com.logistics.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleLocationUpdateDto {
    @NotNull(message = "Latitude is required")
    private Double latitude;
    
    @NotNull(message = "Longitude is required")
    private Double longitude;
}
