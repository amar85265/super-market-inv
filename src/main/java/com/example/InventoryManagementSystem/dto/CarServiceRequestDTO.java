package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarServiceRequestDTO {

    @NotBlank(message = "Service code is required")
    @Size(
            max = 30,
            message = "Service code must not exceed 30 characters"
    )
    private String serviceCode;

    @NotBlank(message = "Service name is required")
    @Size(
            max = 150,
            message = "Service name must not exceed 150 characters"
    )
    private String serviceName;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(
            value = "0.00",
            message = "Price cannot be negative"
    )
    private BigDecimal price;

    @Positive(
            message = "Duration must be greater than 0"
    )
    private Integer durationMinutes;

    @NotNull(message = "Tax percentage is required")
    @DecimalMin(
            value = "0.00",
            message = "Tax cannot be negative"
    )
    @DecimalMax(
            value = "100.00",
            message = "Tax cannot exceed 100"
    )
    private BigDecimal taxPercentage;

    private Boolean active;
}