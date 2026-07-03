package com.example.InventoryManagementSystem.dto;




import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesRequestDTO {

    private Long customerId;
    private Long createdBy;

    private String invoiceNumber;
    private String paymentStatus;

    private BigDecimal totalAmount;
}