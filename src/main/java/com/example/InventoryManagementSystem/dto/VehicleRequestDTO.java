package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleRequestDTO {

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotBlank(message = "Vehicle model is required")
    private String vehicleModel;

    private String registrationNumber;
    private Integer odometer;
    private String vehicleType;
    private String fuelType;
    private Integer year;
}
