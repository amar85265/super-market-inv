package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseItemId;

    private Long purchaseId;

    private Long productId;

    private Integer quantity;

    private BigDecimal purchasePrice;

    private BigDecimal total;

    @PrePersist
    @PreUpdate
    public void calculateTotal() {

        if (purchasePrice != null && quantity != null) {

            this.total = purchasePrice.multiply(
                    BigDecimal.valueOf(quantity)
            );
        }
    }
}