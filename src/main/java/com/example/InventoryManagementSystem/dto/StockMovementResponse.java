package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementResponse {

    private String movementId;

    private String productId;

    private String productName;

    private String movementType;

    private Integer quantity;

    private String referenceId;

    private String notes;

    private OffsetDateTime createdAt;
}