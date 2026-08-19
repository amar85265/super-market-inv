package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class VehicleResponseDTO {

    private Long vehicleId;
    private Long customerId;
    private String customerName;
    private String vehicleModel;
    private String registrationNumber;
    private Integer odometer;
    private String vehicleType;
    private String fuelType;
    private Integer year;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
