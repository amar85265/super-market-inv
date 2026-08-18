package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponseDTO {

    private String vehicleId;

    private String customerId;

    private String registrationNumber;

    private String vinNumber;

    private String vehicleType;

    private String brand;

    private String model;

    private String variant;

    private Integer manufacturingYear;

    private String color;

    private String fuelType;

    private Integer currentOdometer;

    private Integer lastServiceOdometer;

    private String status;

    private Boolean active;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}