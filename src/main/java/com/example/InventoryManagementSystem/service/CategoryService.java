package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.CategoryRequest;
import com.example.InventoryManagementSystem.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
