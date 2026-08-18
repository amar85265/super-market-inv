package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    Optional<Vehicle> findByRegistrationNumberIgnoreCase(
            String registrationNumber
    );

    Optional<Vehicle> findByVinNumberIgnoreCase(
            String vinNumber
    );

    boolean existsByRegistrationNumberIgnoreCase(
            String registrationNumber
    );

    boolean existsByVinNumberIgnoreCase(
            String vinNumber
    );

    List<Vehicle> findByCustomerId(String customerId);

    List<Vehicle> findByCustomerIdAndActiveTrue(String customerId);

    List<Vehicle> findByActiveTrue();

    List<Vehicle> findByBrandContainingIgnoreCase(
            String brand
    );

    List<Vehicle> findByModelContainingIgnoreCase(
            String model
    );

    List<Vehicle> findByRegistrationNumberContainingIgnoreCase(
            String registrationNumber
    );
}