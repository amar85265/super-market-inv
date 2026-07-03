package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalesResponseDTO {

    private String saleId;
    private String customerId;
    private String customerName;
    private Long createdBy;
    private String invoiceNumber;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private LocalDateTime saleDate;
    private List<com.example.InventoryManagementSystem.dto.SalesItemResponseDTO> items;
}