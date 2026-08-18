package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
<<<<<<< Updated upstream
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
=======
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "invoice_id")
    private String invoiceId;
>>>>>>> Stashed changes

    @Column(name = "invoice_number", nullable = false, unique = true, length = 30)
    private String invoiceNumber;

<<<<<<< Updated upstream
    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long counterId;
=======
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "vehicle_id", nullable = false)
    private String vehicleId;
>>>>>>> Stashed changes

    @Column(name = "payment_method", nullable = false, length = 20)
    private String paymentMethod;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

<<<<<<< Updated upstream
    @Column(nullable = false)
    private Long createdBy;
=======
    @Column(name = "tax_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxAmount;
>>>>>>> Stashed changes

    @Column(name = "grand_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal grandTotal;

    @Column(name = "invoice_date", nullable = false)
    private LocalDateTime invoiceDate;

    @OneToMany(
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<InvoiceItem> items = new ArrayList<>();
}