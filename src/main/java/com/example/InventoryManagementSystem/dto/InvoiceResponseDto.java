package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponseDTO {

    private Long invoiceId;

    private String invoiceNumber;

    private Long customerId;

<<<<<<< Updated upstream
    private Long counterId;
=======
    private String vehicleId;

    private String paymentMethod;

    private LocalDateTime invoiceDate;
>>>>>>> Stashed changes

    private BigDecimal subtotal;

    private BigDecimal taxAmount;

    private BigDecimal grandTotal;

<<<<<<< Updated upstream
    private BigDecimal paidAmount;

    private BigDecimal balanceAmount;

    private String paymentMethod;

    private String paymentStatus;

    private Long createdBy;

    private OffsetDateTime createdAt;
=======
    private List<InvoiceItemResponseDTO> items;
>>>>>>> Stashed changes
}