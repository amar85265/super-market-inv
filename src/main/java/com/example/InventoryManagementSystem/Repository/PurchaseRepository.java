package com.example.Erp.inventorymanagement.repository;

import com.example.Erp.inventorymanagement.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {

}