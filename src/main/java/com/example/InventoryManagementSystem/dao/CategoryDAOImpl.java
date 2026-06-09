package com.example.InventoryManagementSystem.dao;

import com.example.InventoryManagementSystem.model.Category;
import com.example.InventoryManagementSystem.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category saveCategory(Category category) {
        return repository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setStatus(category.getStatus());
        return repository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
