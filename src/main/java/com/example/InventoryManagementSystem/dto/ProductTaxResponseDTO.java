package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductTaxResponseDTO {

    private String taxId;

    private String productId;

    private String taxName;

    private Double taxPercentage;

    private LocalDateTime createdAt;
}