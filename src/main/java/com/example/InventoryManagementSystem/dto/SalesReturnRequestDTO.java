package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SalesReturnRequestDTO {

    @NotNull(message = "Sales Item ID is required")
    private String salesItemId;

    @NotNull(message = "Sale ID is required")
    private String saleId;

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Return quantity is required")
    @Min(value = 1, message = "Return quantity must be >= 1")
    private Integer returnQuantity;

    @NotBlank(message = "Reason is required")
    private String reason;

    @Size(max = 50, message = "Notes cannot exceed 50 characters")
    private String notes;

    // totalAmount is intentionally NOT validated as required —
    // it is calculated by the server, not trusted from the client.
    private java.math.BigDecimal totalAmount;

    // refundStatus is also set/controlled by the server on create.
    private String refundStatus;
}