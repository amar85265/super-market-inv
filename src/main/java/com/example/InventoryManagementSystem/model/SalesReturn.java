package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sales_returns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;

    private String salesItemId;
    private String saleId;
    private String customerId;
    private Integer returnQuantity;
    private String reason;
    private String notes;
    private BigDecimal totalAmount;

    @Builder.Default
    private String refundStatus = "PENDING";

    private OffsetDateTime returnDate;
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
        this.returnDate = OffsetDateTime.now();
    }
}