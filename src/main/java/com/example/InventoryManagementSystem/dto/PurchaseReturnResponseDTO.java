package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnResponseDTO {

    private String purchaseReturnId;

    private String purchaseId;

    private String supplierId;

    private LocalDateTime returnDate;

    private BigDecimal totalAmount;

    private String notes;
}