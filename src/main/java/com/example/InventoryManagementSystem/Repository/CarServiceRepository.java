package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.CarService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarServiceRepository
        extends JpaRepository<CarService, String> {

    boolean existsByServiceCode(String serviceCode);

    boolean existsByServiceName(String serviceName);

    Optional<CarService> findByServiceCode(String serviceCode);

    @Query(
            value = "SELECT nextval('car_service_seq')",
            nativeQuery = true
    )
    Long getNextServiceSequence();
}