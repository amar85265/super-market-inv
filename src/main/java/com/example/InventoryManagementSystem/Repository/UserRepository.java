package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, String> {

    boolean existsByMobileNumber(
            String mobileNumber);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Object> findByEmail(String email);
}