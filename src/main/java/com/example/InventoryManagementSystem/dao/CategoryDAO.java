package com.example.InventoryManagementSystem.dao;

import com.example.InventoryManagementSystem.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {

    Category saveCategory(Category category);

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
