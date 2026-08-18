package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "vehicles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vehicle_registration", columnNames = "registration_number"),
                @UniqueConstraint(name = "uk_vehicle_vin", columnNames = "vin_number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "vehicle_id")
    private String vehicleId;

    // Customer who owns the vehicle
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "vin_number", unique = true)
    private String vinNumber;

    @Column(name = "vehicle_type")
    private String vehicleType;

    private String brand;

    private String model;

    @Column(name = "variant")
    private String variant;

    @Column(name = "manufacturing_year")
    private Integer manufacturingYear;

    @Column(name = "color")
    private String color;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "current_odometer")
    private Integer currentOdometer;

    @Column(name = "last_service_odometer")
    private Integer lastServiceOdometer;

    @Column(name = "status")
    private String status;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


    @PrePersist
    public void prePersist() {

        OffsetDateTime now = OffsetDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.active == null) {
            this.active = true;
        }

        if (this.status == null) {
            this.status = "ACTIVE";
        }
    }


    @PreUpdate
    public void preUpdate() {

        this.updatedAt = OffsetDateTime.now();
    }
}