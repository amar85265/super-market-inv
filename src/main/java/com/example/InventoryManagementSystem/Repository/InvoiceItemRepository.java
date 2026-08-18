package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

<<<<<<< Updated upstream
    List<InvoiceItem> findByInvoiceId(Long invoiceId);

    List<InvoiceItem> findByProductId(Long productId);

    List<InvoiceItem> findByBarcode(String barcode);
=======
    // Get all items belonging to a particular invoice
    List<InvoiceItem> findByInvoice_InvoiceId(String invoiceId);
>>>>>>> Stashed changes

    // Get items by type: PRODUCT or SERVICE
    List<InvoiceItem> findByItemType(String itemType);
}