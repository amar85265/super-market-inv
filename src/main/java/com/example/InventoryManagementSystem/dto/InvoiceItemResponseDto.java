package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemResponseDto {

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
}