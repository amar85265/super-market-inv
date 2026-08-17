package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponseDto {


    private String purchaseId;

    private String supplierName;

    private String invoiceNumber;

    private LocalDateTime purchaseDate;

    private BigDecimal totalAmount;

    private BigDecimal tax;

    private String paymentStatus;

    private String createdBy;


}
