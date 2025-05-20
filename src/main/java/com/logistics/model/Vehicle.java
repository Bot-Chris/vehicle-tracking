// File: src/main/java/com/logistics/model/Vehicle.java
package com.logistics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank
    @Column(unique = true)
    private String registrationNumber;
    
    @NotBlank
    private String model;
    
    @NotBlank
    private String type;
    
    @NotNull
    private Double capacityKg;
    
    private Double fuelCapacityLiters;
    
    private String status;
    
    private LocalDateTime lastMaintenanceDate;
    
    private Double latitude;
    
    private Double longitude;
    
    private LocalDateTime lastLocationUpdate;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}