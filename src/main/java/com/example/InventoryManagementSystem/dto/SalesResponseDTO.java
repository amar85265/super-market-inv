package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalesResponseDTO {

    private Long saleId;

    private Integer customerId;

    private String invoiceNumber;

    private LocalDateTime saleDate;

    private BigDecimal totalAmount;

    private String paymentStatus;

    private Integer createdBy;


}
