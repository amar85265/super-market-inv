package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SalesRequestDTO {

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Created By is required")
    @Positive(message = "Created By must be greater than 0")
    private Long createdBy;

    @NotBlank(message = "Invoice number is required")
    @Size(max = 100, message = "Invoice number cannot exceed 100 characters")
    private String invoiceNumber;

    @NotBlank(message = "Payment status is required")
    @Pattern(
            regexp = "PAID|PENDING|PARTIAL",
            message = "Payment status must be PAID, PENDING or PARTIAL"
    )
    private String paymentStatus;

    @NotNull(message = "Items are required")
    @Size(min = 1, message = "At least one item is required")
    private List<SalesItemRequestDTO> items;
}