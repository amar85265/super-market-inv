package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesRequestDTO {


    private Integer customerId;

    private String invoiceNumber;

    private BigDecimal totalAmount;

    private String paymentStatus;

    private Integer createdBy;


}
