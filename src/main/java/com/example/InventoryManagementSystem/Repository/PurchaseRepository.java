package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository
        extends JpaRepository<Purchase, String> {

    Optional<Purchase> findTopByOrderByPurchaseIdDesc();
}