package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDTO {

    @NotBlank(message = "Customer name is required")
    @Size(min = 3, max = 100,
            message = "Customer name must be between 3 and 100 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Customer name must contain only letters and spaces"
    )
    private String customerName;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Phone number must be a valid 10-digit Indian mobile number"
    )
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Pattern(
            regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$",
            message = "Email must contain only lowercase letters"
    )
    private String email;

    @NotBlank(message = "Location is required")
    @Size(min = 20, max = 255,
            message = "Location must be between 20 and 255 characters")
    @Pattern(
            regexp = "^\\d+[A-Za-z0-9/\\-]*,\\s*[A-Za-z ]+,\\s*[A-Za-z ]+,\\s*[A-Za-z ]+,\\s*[A-Za-z ]+,\\s*\\d{6}$",
            message = "Location must be in format: Door No, Street, Area, City, State, PIN Code"
    )

    private String address;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "active|inactive",
            message = "Status must be active or inactive"
    )
    private String status;
}