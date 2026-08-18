package com.example.InventoryManagementSystem.dto;



import lombok.Data;

@Data
public class CustomerRequestDTO {
    private String customerName;
    private String phone;
    private String email;
<<<<<<< Updated upstream
=======

    @NotBlank(message = "Address is required")
    @Size(min = 10, max = 255,
            message = "Address must be between 10 and 255 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9.,/\\- ]+$",
            message = "Address can contain only letters, numbers, spaces, comma, dot, slash and hyphen"
    )
>>>>>>> Stashed changes
    private String address;
    private String status;
}