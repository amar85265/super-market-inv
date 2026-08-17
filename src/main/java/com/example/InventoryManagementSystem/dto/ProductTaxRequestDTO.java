package com.example.InventoryManagementSystem.dto;


import lombok.Data;

@Data
public class ProductTaxRequestDTO {

    private String productId;
    private String taxName;
    private Double taxPercentage;
}