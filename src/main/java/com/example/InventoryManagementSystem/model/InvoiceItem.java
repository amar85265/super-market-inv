package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItem {

    @Id
<<<<<<< Updated upstream
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceItemId;

    @Column(nullable = false)
    private Long invoiceId;

    @Column(nullable = false)
    private Long productId;
=======
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "invoice_item_id")
    private String invoiceItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "item_type", nullable = false, length = 20)
    private String itemType;

    @Column(name = "product_id")
    private String productId;
>>>>>>> Stashed changes

    @Column(name = "service_id")
    private String serviceId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal rate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
}