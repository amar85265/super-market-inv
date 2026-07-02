package com.example.InventoryManagementSystem.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private Long Id;

    private String username;

    private String fullName;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String roleName;

    private String status;

    private Boolean active;

    private String createdAt;

    private String updatedAt;
}