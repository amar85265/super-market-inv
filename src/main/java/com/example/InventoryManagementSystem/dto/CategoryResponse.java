package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;

    private String categoryName;

    private String description;

    private String status;

    private OffsetDateTime createdAt;
}
