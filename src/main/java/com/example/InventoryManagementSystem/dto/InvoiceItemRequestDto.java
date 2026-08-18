package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItemRequestDTO {

<<<<<<< Updated upstream
    @NotNull(message = "Invoice ID is required")
    @Positive(message = "Invoice ID must be greater than 0")
    private Long invoiceId;

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be greater than 0")
    private Long productId;
=======
    @NotBlank(message = "Item type is required")
    private String itemType;

    private String productId;
>>>>>>> Stashed changes

    private String serviceId;

    private String description;

    private String unit;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private BigDecimal quantity;

    private BigDecimal rate;
}