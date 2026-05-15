package com.example.InventoryManagementSystem.Dto;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String username;

    private String password;

    private String email;

    private String fullName;

    private Integer roleId;

    private String status;
}