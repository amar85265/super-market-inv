package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItemResponseDTO {

<<<<<<< Updated upstream
    private Long invoiceItemId;

    private Long invoiceId;

    private Long productId;

    private String productName;

    private String barcode;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal discount;

    private BigDecimal taxPercentage;

    private BigDecimal taxAmount;

    private BigDecimal lineTotal;
=======
    private String invoiceItemId;

    private String itemType;

    private String productId;

    private String serviceId;

    private String description;

    private String unit;

    private BigDecimal quantity;

    private BigDecimal rate;

    private BigDecimal amount;
>>>>>>> Stashed changes
}