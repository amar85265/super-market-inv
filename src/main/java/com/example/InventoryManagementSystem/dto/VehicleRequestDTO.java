package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDTO {

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Registration number is required")
    @Size(max = 20, message = "Registration number cannot exceed 20 characters")
    private String registrationNumber;

    @Size(max = 50, message = "VIN number cannot exceed 50 characters")
    private String vinNumber;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    private String variant;

    @Min(value = 1900, message = "Invalid manufacturing year")
    private Integer manufacturingYear;

    private String color;

    private String fuelType;

    @Min(value = 0, message = "Odometer cannot be negative")
    private Integer currentOdometer;

    private Integer lastServiceOdometer;

    private String status;

    private Boolean active;
}