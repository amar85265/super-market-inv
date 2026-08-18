package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "car_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarService {

    @Id
    @Column(
            name = "service_id",
            length = 30,
            nullable = false,
            updatable = false
    )
    private String serviceId;

    @Column(
            name = "service_code",
            length = 30,
            nullable = false,
            unique = true
    )
    private String serviceCode;

    @Column(
            name = "service_name",
            length = 150,
            nullable = false,
            unique = true
    )
    private String serviceName;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "price",
            precision = 12,
            scale = 2,
            nullable = false
    )
    private BigDecimal price;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(
            name = "tax_percentage",
            precision = 5,
            scale = 2,
            nullable = false
    )
    private BigDecimal taxPercentage;

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active;

    @Column(
            name = "created_at",
            nullable = false
    )
    private OffsetDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        if (price == null) {
            price = BigDecimal.ZERO;
        }

        if (taxPercentage == null) {
            taxPercentage = BigDecimal.ZERO;
        }

        if (active == null) {
            active = true;
        }

        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}