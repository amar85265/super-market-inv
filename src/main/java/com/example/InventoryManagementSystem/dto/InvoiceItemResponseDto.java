package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemResponseDto {

        private String invoiceItemId;

        private String invoiceId;

        private String productId;

        private String productName;

        private String barcode;

        private BigDecimal quantity;

        private BigDecimal unitPrice;

        private BigDecimal discount;

        private BigDecimal taxPercentage;

        private BigDecimal taxAmount;

        private BigDecimal lineTotal;
}