package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Purchase;
import com.example.InventoryManagementSystem.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository
        extends JpaRepository<Purchase, Long> {

    boolean existsBySupplierAndInvoiceNumber(
            Supplier supplier,
            String invoiceNumber);
}