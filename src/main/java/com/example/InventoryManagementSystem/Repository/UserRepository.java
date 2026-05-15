package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}