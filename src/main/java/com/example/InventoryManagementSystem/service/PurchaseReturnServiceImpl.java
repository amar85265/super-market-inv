package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.PurchaseRepository;
import com.example.InventoryManagementSystem.Repository.PurchaseReturnRepository;
import com.example.InventoryManagementSystem.Repository.SupplierRepository;
import com.example.InventoryManagementSystem.dto.PurchaseReturnRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnResponseDTO;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.Purchase;
import com.example.InventoryManagementSystem.model.PurchaseReturn;
import com.example.InventoryManagementSystem.model.Supplier;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseReturnServiceImpl
        implements PurchaseReturnService {

    private final PurchaseReturnRepository purchaseReturnRepository;

    private final PurchaseRepository purchaseRepository;

    private final SupplierRepository supplierRepository;

    private final ProductRepository productRepository;


    // =========================================================
    // CREATE PURCHASE RETURN
    // =========================================================

    @Override
    @Transactional
    public PurchaseReturnResponseDTO createPurchaseReturn(
            PurchaseReturnRequestDTO requestDTO) {

        // 1. Find Purchase
        Purchase purchase = purchaseRepository
                .findById(requestDTO.getPurchaseId())
                .orElseThrow(() ->
                        new RuntimeException("Purchase not found"));


        // 2. Find Supplier
        Supplier supplier = supplierRepository
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found"));


        // 3. Find Product
        Product product = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));


        // 4. Validate return quantity
        Integer returnQuantity = requestDTO.getQuantity();

        if (returnQuantity == null || returnQuantity <= 0) {

            throw new RuntimeException(
                    "Return quantity must be greater than zero"
            );
        }


        // 5. Check available stock
        if (product.getStockQuantity() == null) {

            throw new RuntimeException(
                    "Product stock is not available"
            );
        }

        if (product.getStockQuantity() < returnQuantity) {

            throw new RuntimeException(
                    "Insufficient stock for purchase return"
            );
        }


        // 6. DECREASE PRODUCT STOCK
        product.setStockQuantity(
                product.getStockQuantity() - returnQuantity
        );


        // 7. Save updated product
        productRepository.save(product);


        // 8. Create Purchase Return
        PurchaseReturn entity = PurchaseReturn.builder()

                .purchaseId(
                        purchase.getPurchaseId() != null
                                ? Math.toIntExact(
                                purchase.getPurchaseId())
                                : null
                )

                .supplierId(
                        supplier.getSupplierId() != null
                                ? Math.toIntExact(
                                supplier.getSupplierId())
                                : null
                )

                .productId(
                        requestDTO.getProductId()
                )

                .quantity(
                        requestDTO.getQuantity()
                )

                .returnDate(
                        LocalDateTime.now()
                )

                .totalAmount(
                        requestDTO.getTotalAmount()
                )

                .notes(
                        requestDTO.getNotes()
                )

                .build();


        // 9. Save Purchase Return
        PurchaseReturn saved =
                purchaseReturnRepository.save(entity);


        // 10. Return response
        return mapToResponse(saved);
    }


    // =========================================================
    // GET PURCHASE RETURN BY ID
    // =========================================================

    @Override
    public PurchaseReturnResponseDTO getPurchaseReturnById(
            Integer id) {

        PurchaseReturn entity =
                purchaseReturnRepository.findById(id)
                        .orElse(null);

        if (entity == null) {
            return null;
        }

        return mapToResponse(entity);
    }


    // =========================================================
    // GET ALL PURCHASE RETURNS
    // =========================================================

    @Override
    public List<PurchaseReturnResponseDTO>
    getAllPurchaseReturns() {

        return purchaseReturnRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    // =========================================================
    // UPDATE PURCHASE RETURN
    // =========================================================

    @Override
    @Transactional
    public PurchaseReturnResponseDTO updatePurchaseReturn(
            Integer id,
            PurchaseReturnRequestDTO requestDTO) {

        // 1. Find existing return
        PurchaseReturn entity =
                purchaseReturnRepository.findById(id)
                        .orElse(null);

        if (entity == null) {
            return null;
        }


        // Old product and quantity
        Long oldProductId = entity.getProductId();

        Integer oldQuantity = entity.getQuantity();


        // 2. Find new purchase
        Purchase purchase = purchaseRepository
                .findById(requestDTO.getPurchaseId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found"
                        ));


        // 3. Find new supplier
        Supplier supplier = supplierRepository
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found"
                        ));


        // 4. Find new product
        Product newProduct = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        ));


        // 5. Validate quantity
        Integer newQuantity = requestDTO.getQuantity();

        if (newQuantity == null || newQuantity <= 0) {

            throw new RuntimeException(
                    "Return quantity must be greater than zero"
            );
        }


        // =====================================================
        // STOCK ADJUSTMENT
        // =====================================================

        // CASE 1:
        // Same product
        if (oldProductId != null &&
                oldProductId.equals(requestDTO.getProductId())) {

            /*
             * Example:
             *
             * Old return = 5
             * New return = 8
             *
             * Difference = 3
             *
             * Stock should decrease by only 3.
             */

            int difference =
                    newQuantity - oldQuantity;

            if (difference > 0) {

                // Need to decrease additional stock
                if (newProduct.getStockQuantity() == null ||
                        newProduct.getStockQuantity() < difference) {

                    throw new RuntimeException(
                            "Insufficient stock for updated purchase return"
                    );
                }

                newProduct.setStockQuantity(
                        newProduct.getStockQuantity()
                                - difference
                );

            } else if (difference < 0) {

                // Return quantity reduced,
                // so give stock back
                newProduct.setStockQuantity(
                        newProduct.getStockQuantity()
                                + Math.abs(difference)
                );
            }


            productRepository.save(newProduct);

        }

        // CASE 2:
        // Product changed
        else {

            /*
             * Give back the old returned quantity
             * to the old product.
             */

            if (oldProductId != null &&
                    oldQuantity != null) {

                Product oldProduct =
                        productRepository
                                .findById(oldProductId)
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Old product not found"
                                        ));

                oldProduct.setStockQuantity(
                        oldProduct.getStockQuantity()
                                + oldQuantity
                );

                productRepository.save(oldProduct);
            }


            /*
             * Decrease stock from new product.
             */

            if (newProduct.getStockQuantity() == null ||
                    newProduct.getStockQuantity() < newQuantity) {

                throw new RuntimeException(
                        "Insufficient stock for updated purchase return"
                );
            }

            newProduct.setStockQuantity(
                    newProduct.getStockQuantity()
                            - newQuantity
            );

            productRepository.save(newProduct);
        }


        // =====================================================
        // UPDATE PURCHASE RETURN
        // =====================================================

        entity.setPurchaseId(
                purchase.getPurchaseId() != null
                        ? Math.toIntExact(
                        purchase.getPurchaseId())
                        : null
        );


        entity.setSupplierId(
                supplier.getSupplierId() != null
                        ? Math.toIntExact(
                        supplier.getSupplierId())
                        : null
        );


        entity.setProductId(
                requestDTO.getProductId()
        );


        entity.setQuantity(
                requestDTO.getQuantity()
        );


        entity.setTotalAmount(
                requestDTO.getTotalAmount()
        );


        entity.setNotes(
                requestDTO.getNotes()
        );


        // 6. Save updated return
        PurchaseReturn updated =
                purchaseReturnRepository.save(entity);


        // 7. Return response
        return mapToResponse(updated);
    }


    // =========================================================
    // DELETE PURCHASE RETURN
    // =========================================================

    @Override
    @Transactional
    public void deletePurchaseReturn(Integer id) {

        PurchaseReturn entity =
                purchaseReturnRepository.findById(id)
                        .orElse(null);

        if (entity == null) {
            return;
        }


        /*
         * When deleting a purchase return,
         * restore the returned quantity to stock.
         */

        if (entity.getProductId() != null &&
                entity.getQuantity() != null) {

            Product product =
                    productRepository
                            .findById(entity.getProductId())
                            .orElse(null);

            if (product != null) {

                product.setStockQuantity(
                        product.getStockQuantity()
                                + entity.getQuantity()
                );

                productRepository.save(product);
            }
        }


        // Delete purchase return
        purchaseReturnRepository.delete(entity);
    }


    // =========================================================
    // MAP ENTITY → RESPONSE DTO
    // =========================================================

    private PurchaseReturnResponseDTO mapToResponse(
            PurchaseReturn entity) {

        return PurchaseReturnResponseDTO.builder()

                .purchaseReturnId(
                        entity.getPurchaseReturnId()
                )

                .purchaseId(
                        entity.getPurchaseId()
                )

                .supplierId(
                        entity.getSupplierId()
                )

                .productId(
                        entity.getProductId()
                )

                .quantity(
                        entity.getQuantity()
                )

                .returnDate(
                        entity.getReturnDate()
                )

                .totalAmount(
                        entity.getTotalAmount()
                )

                .notes(
                        entity.getNotes()
                )

                .build();
    }
}