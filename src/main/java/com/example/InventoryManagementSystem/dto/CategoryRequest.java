package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50,
            message = "Category name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Category name must contain only letters and spaces"
    )
    private String categoryName;

    @NotBlank(message = "Description is required")
    @Size(max = 255,
            message = "Description cannot exceed 255 characters")

    private String description;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "active|inactive",
            message = "Status must be active or inactive"
    )
    private String status;
}