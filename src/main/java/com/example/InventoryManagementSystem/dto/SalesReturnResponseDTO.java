package com.example.InventoryManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class SalesReturnResponseDTO {

    private Long returnId;
    private String salesItemId;
    private String saleId;
    private String customerId;
    private Integer returnQuantity;
    private String reason;
    private String notes;
    private BigDecimal totalAmount;
    private String refundStatus;
    private OffsetDateTime returnDate;
    private OffsetDateTime createdAt;
}