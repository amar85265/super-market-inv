package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findTopByOrderByCustomerIdDesc();

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}