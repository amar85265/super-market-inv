package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< Updated upstream
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

=======
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    boolean existsByInvoiceNumber(String invoiceNumber);
>>>>>>> Stashed changes
}