package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BillingCounterDto {

    private Long counterId;

    @NotBlank(message = "Counter name is required")
    @Pattern(
            regexp = "^(Main Counter|Billing Counter|Cash Counter|Customer Service Counter|Ground Floor Counter|Food Court Counter|Electronics Counter|Fashion Counter|Supermarket Counter|Reception Counter|Help Desk Counter|Payment Counter)$",
            message = "Invalid counter name"
    )
    private String counterName;

    @NotBlank(message = "Location is required")
    @Pattern(
            regexp = "^(Ground Floor|First Floor|Second Floor|Third Floor|Fourth Floor|Fifth Floor)$",
            message = "Location must be Ground Floor, First Floor, Second Floor, Third Floor, Fourth Floor or Fifth Floor"
    )
    private String location;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "active|inactive",
            message = "Status must be active or inactive"
    )
    private String status;

    // Getters and Setters

    public Long getCounterId() {
        return counterId;
    }

    public void setCounterId(Long counterId) {
        this.counterId = counterId;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}