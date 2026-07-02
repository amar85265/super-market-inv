package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CustomerResponseDTO {

    private String customerId;
    private String customerCode;
    private String customerName;
    private String phone;
    private String email;
    private String address;
    private String status;
    private OffsetDateTime createdAt;
}