package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20,
            message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "First Name is required")
    @Size(max = 50,
            message = "First Name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 50,
            message = "Last Name cannot exceed 50 characters")
    private String lastName;


    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[a-z][a-z0-9._%+-]*@[a-z0-9.-]+\\.[a-z]{2,}$",
            message = "Email must start with a lowercase letter and contain only lowercase characters"
    )
    private String email;
    @NotBlank(message = "Mobile Number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Enter a valid 10 digit mobile number"
    )
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;

    @NotNull(message = "Role Id is required")
    private Integer roleId;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "ACTIVE|INACTIVE",
            message = "Status must be ACTIVE or INACTIVE"
    )
    private String status;

    @NotNull(message = "Active field is required")
    private Boolean active;
}