package com.example.InventoryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesResponseDTO {

    private Long saleId;
    private Long customerId;
    private String customerName;
    private Long createdBy;
    private String invoiceNumber;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private LocalDateTime saleDate;
    private List<SalesItemResponseDTO> items;
}