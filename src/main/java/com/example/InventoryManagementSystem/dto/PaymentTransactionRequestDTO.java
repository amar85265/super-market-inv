package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class PaymentTransactionRequestDTO {

    @NotNull(message = "Invoice ID is required")
    private String invoiceId;

    @NotBlank(message = "Payment method is required")
    @Pattern(
            regexp = "CASH|CARD|UPI|NETBANKING",
            message = "Payment method must be CASH, CARD, UPI, or NETBANKING"
    )
    private String paymentMethod;

    @NotBlank(message = "Transaction reference is required")
    @Size(min = 5, max = 50,
            message = "Transaction reference must be between 5 and 50 characters")
    private String transactionReference;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01",
            message = "Amount must be greater than 0")
    private BigDecimal amount;

}