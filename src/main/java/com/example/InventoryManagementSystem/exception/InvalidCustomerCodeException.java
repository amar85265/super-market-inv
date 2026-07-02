package com.example.InventoryManagementSystem.exception;

public class InvalidCustomerCodeException extends RuntimeException {
    public InvalidCustomerCodeException(String message) {
        super(message);
    }
}
