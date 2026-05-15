package com.example.InventoryManagementSystem.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserResponseDTO {

    private Integer userId;

    private String username;

    private String email;

    private String fullName;

    private String roleName;

    private String status;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}