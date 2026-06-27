package com.example.InventoryManagementSystem.dto;

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

    private Integer purchaseReturnId;
    private List<PurchaseReturnItemRequestDTO> items;
    private Long purchaseId;
    private Long supplierId;
    private LocalDateTime returnDate;
    private BigDecimal totalAmount;
    private String notes;
}