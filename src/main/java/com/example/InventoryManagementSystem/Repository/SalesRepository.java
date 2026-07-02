package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, String> {

    Optional<Sales> findTopByOrderBySaleIdDesc();

    boolean existsByInvoiceNumber(String invoiceNumber);
}