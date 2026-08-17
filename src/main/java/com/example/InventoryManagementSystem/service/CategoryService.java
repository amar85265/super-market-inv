package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.CategoryRequest;
import com.example.InventoryManagementSystem.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(String categoryId);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(String categoryId, CategoryRequest request);

    void deleteCategory(String categoryId);
}