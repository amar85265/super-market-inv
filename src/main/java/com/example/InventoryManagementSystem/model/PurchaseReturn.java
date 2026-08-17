package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_returns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "purchase_return_id")
    private String purchaseReturnId;

    @NotNull(message = "Purchase ID is required")
    @Column(name = "purchase_id")
    private String purchaseId;

    @NotNull(message = "Supplier ID is required")
    @Column(name = "supplier_id")
    private String supplierId;

    @NotNull(message = "Return date is required")
    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    @Column(name = "notes")
    private String notes;
}