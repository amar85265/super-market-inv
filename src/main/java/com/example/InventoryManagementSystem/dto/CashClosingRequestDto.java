package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashClosingRequestDto {

    @NotNull(message = "Counter ID is required")
    @Positive(message = "Counter ID must be greater than 0")
    private Long counterId;

    @NotNull(message = "Opening cash is required")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "Opening cash must be greater than 0"
    )
    private BigDecimal openingCash;

    @NotNull(message = "Closing cash is required")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "Closing cash must be greater than 0"
    )
    private BigDecimal closingCash;

    @NotNull(message = "Total sales is required")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "Total sales must be greater than 0"
    )
    private BigDecimal totalSales;
}