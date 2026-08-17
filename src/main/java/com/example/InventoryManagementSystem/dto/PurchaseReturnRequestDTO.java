package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnRequestDTO {

    private String purchaseReturnId;
    private List<PurchaseReturnItemRequestDTO> items;

    @NotBlank(message = "Purchase ID is required")
    private String purchaseId;

    @NotBlank(message = "Supplier ID is required")
    private String supplierId;
    private LocalDateTime returnDate;
    private BigDecimal totalAmount;
    private String notes;
}