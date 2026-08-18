package com.example.InventoryManagementSystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequestDTO {

<<<<<<< Updated upstream
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be greater than 0")
    private Long customerId;

    @NotNull(message = "Counter ID is required")
    @Positive(message = "Counter ID must be greater than 0")
    private Long counterId;
=======
    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Vehicle ID is required")
    private String vehicleId;
>>>>>>> Stashed changes

    private String invoiceNumber;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

<<<<<<< Updated upstream
    @NotNull(message = "Created By is required")
    @Positive(message = "Created By must be greater than 0")
    private Long createdBy;
=======
    private LocalDateTime invoiceDate;

    @NotEmpty(message = "At least one invoice item is required")
    @Valid
    private List<InvoiceItemRequestDTO> items;
>>>>>>> Stashed changes
}