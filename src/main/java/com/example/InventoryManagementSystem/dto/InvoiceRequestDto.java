package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceRequestDto {

    @NotNull(message = "Customer ID is required")
    @NotBlank(message = "Customer ID cannot be blank")
    private String customerId;

    @NotNull(message = "Counter ID is required")
    @NotBlank(message = "Counter ID cannot be blank")
    private String counterId;

    @NotNull(message = "Paid Amount is required")
    @DecimalMin(value = "0.00", message = "Paid Amount cannot be negative")
    private BigDecimal paidAmount;

    @NotBlank(message = "Payment Method is required")
    @Pattern(
            regexp = "^(CASH|CARD|UPI|NET_BANKING)$",
            message = "Payment Method must be CASH, CARD, UPI or NET_BANKING"
    )
    private String paymentMethod;

    @NotNull(message = "Created By is required")
    @Positive(message = "Created By must be greater than 0")
    private Long createdBy;
}