package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @NotNull(message = "Supplier is required")
    private Supplier supplier;

    @NotBlank(message = "Invoice number is required")
    @Column(name = "invoice_number")
    private String invoiceNumber;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Total amount must be greater than 0")
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @NotNull(message = "Tax is required")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Tax cannot be negative")
    @Column(name = "tax")
    private BigDecimal tax;

    @NotBlank(message = "Payment status is required")
    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @NotNull(message = "Created By user is required")
    private User createdBy;

    @PrePersist
    public void prePersist() {
        this.purchaseDate = LocalDateTime.now();
    }

}