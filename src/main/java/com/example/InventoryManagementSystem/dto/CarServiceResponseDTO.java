package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarServiceResponseDTO {

    private String serviceId;

    private String serviceCode;

    private String serviceName;

    private String description;

    private BigDecimal price;

    private Integer durationMinutes;

    private BigDecimal taxPercentage;

    private Boolean active;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}