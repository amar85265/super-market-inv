package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.CarServiceRequestDTO;
import com.example.InventoryManagementSystem.dto.CarServiceResponseDTO;
import com.example.InventoryManagementSystem.model.CarService;
import com.example.InventoryManagementSystem.Repository.CarServiceRepository;
import com.example.InventoryManagementSystem.service.CarServiceService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceServiceImpl implements CarServiceService {

    private final CarServiceRepository carServiceRepository;

    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public CarServiceResponseDTO createService(CarServiceRequestDTO request) {

        if (carServiceRepository.existsByServiceCode(request.getServiceCode())) {
            throw new RuntimeException("Service code already exists");
        }

        if (carServiceRepository.existsByServiceName(request.getServiceName())) {
            throw new RuntimeException("Service name already exists");
        }

        CarService carService = CarService.builder()
                .serviceId(generateServiceId())
                .serviceCode(request.getServiceCode())
                .serviceName(request.getServiceName())
                .description(request.getDescription())
                .price(request.getPrice())
                .durationMinutes(request.getDurationMinutes())
                .taxPercentage(request.getTaxPercentage())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();

        CarService savedService = carServiceRepository.save(carService);

        return mapToResponse(savedService);
    }

    @Override
    public List<CarServiceResponseDTO> getAllServices() {

        return carServiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CarServiceResponseDTO getServiceById(String serviceId) {

        CarService carService = carServiceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException("Car service not found with ID: " + serviceId)
                );

        return mapToResponse(carService);
    }

    @Override
    public CarServiceResponseDTO updateService(
            String serviceId,
            CarServiceRequestDTO request
    ) {

        CarService carService = carServiceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException("Car service not found with ID: " + serviceId)
                );

        carService.setServiceCode(request.getServiceCode());
        carService.setServiceName(request.getServiceName());
        carService.setDescription(request.getDescription());
        carService.setPrice(request.getPrice());
        carService.setDurationMinutes(request.getDurationMinutes());
        carService.setTaxPercentage(request.getTaxPercentage());

        if (request.getActive() != null) {
            carService.setActive(request.getActive());
        }

        CarService updatedService = carServiceRepository.save(carService);

        return mapToResponse(updatedService);
    }

    @Override
    public void deleteService(String serviceId) {

        CarService carService = carServiceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException("Car service not found with ID: " + serviceId)
                );

        carServiceRepository.delete(carService);
    }

    private String generateServiceId() {

        return String.format(
                "CS-%04d",
                counter.incrementAndGet()
        );
    }

    private CarServiceResponseDTO mapToResponse(CarService service) {

        return CarServiceResponseDTO.builder()
                .serviceId(service.getServiceId())
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .description(service.getDescription())
                .price(service.getPrice())
                .durationMinutes(service.getDurationMinutes())
                .taxPercentage(service.getTaxPercentage())
                .active(service.getActive())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }
}