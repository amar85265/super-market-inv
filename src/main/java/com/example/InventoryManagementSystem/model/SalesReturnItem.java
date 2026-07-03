package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * This is the database table model for sales_return_items.
 *
 * One Sales Return can have many Sales Return Items.
 * Example:
 *   Sales Return ID = 1 (customer returning items from SALE-021)
 *     -> Return Item 1: Notebook, qty 1, price 100, total 100
 *     -> Return Item 2: Pen,      qty 1, price 50,  total 50
 */
@Entity
@Table(name = "sales_return_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnItem {

    @Id
    @Column(name = "sales_return_item_id", length = 20)
    private String salesReturnItemId; // We generate this manually: SRITEM-001, SRITEM-002...

    private String salesReturnId; // Links to the parent Sales Return

    private String productId;     // Which product is being returned

    private Integer quantity;     // How many units are returned

    private BigDecimal price;     // Price per unit (copied from original sale)

    private BigDecimal total;     // Total = price x quantity
}