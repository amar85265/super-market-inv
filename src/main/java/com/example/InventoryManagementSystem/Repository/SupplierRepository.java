package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, String> {

    boolean existsByEmail(String email);
}