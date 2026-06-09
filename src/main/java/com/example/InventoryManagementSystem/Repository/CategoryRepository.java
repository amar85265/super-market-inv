package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
