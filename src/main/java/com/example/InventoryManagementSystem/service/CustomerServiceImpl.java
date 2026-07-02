package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.CustomerRequestDTO;
import com.example.InventoryManagementSystem.dto.CustomerResponseDTO;
import com.example.InventoryManagementSystem.model.Customer;
import com.example.InventoryManagementSystem.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    private String generateCustomerId() {
        Optional<Customer> lastCustomerOpt =
                repository.findTopByOrderByCustomerIdDesc();

        if (lastCustomerOpt.isEmpty()) {
            return "CRN-001";
        }

        String lastId = lastCustomerOpt.get().getCustomerId();
        int lastNumber = 0;

        if (lastId != null && lastId.startsWith("CRN-")) {
            lastNumber = Integer.parseInt(lastId.substring(4));
        }

        return String.format("CRN-%03d", lastNumber + 1);
    }

    private Customer mapToEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerName(dto.getCustomerName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setStatus(dto.getStatus());
        return customer;
    }

    private CustomerResponseDTO mapToDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setCustomerCode(customer.getCustomerCode());
        dto.setCustomerName(customer.getCustomerName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setStatus(customer.getStatus());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (repository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        Customer customer = mapToEntity(dto);

        String generatedId = generateCustomerId();
        customer.setCustomerId(generatedId);
        customer.setCustomerCode(generatedId);

        return mapToDTO(repository.save(customer));
    }

    @Override
    public CustomerResponseDTO getCustomerById(String id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found with id: " + id));
        return mapToDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO updateCustomer(String id, CustomerRequestDTO dto) {
        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found with id: " + id));

        customer.setCustomerName(dto.getCustomerName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setStatus(dto.getStatus());

        return mapToDTO(repository.save(customer));
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found with id: " + id));
        repository.delete(customer);
    }
}