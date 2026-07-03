package com.example.InventoryManagementSystem.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalesResponseDTO {

    private Long saleId;
    private Long customerId;
    private Long createdBy;
    private Long getSalesReturnId;
    private String invoiceNumber;
    private String paymentStatus;

    private BigDecimal totalAmount;

    private LocalDateTime saleDate;
}