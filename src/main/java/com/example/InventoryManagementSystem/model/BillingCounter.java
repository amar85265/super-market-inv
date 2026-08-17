package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "billing_counters")
public class BillingCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "counter_id")
    private String counterId;

    @Column(name = "counter_name", length = 50)
    private String counterName;

    @Column(length = 100)
    private String location;

    @Column(length = 20)
    private String status = "active";

    // Getters and Setters

    public String getCounterId() {
        return counterId;
    }

    public void setCounterId(String counterId) {
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