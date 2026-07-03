package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.SalesReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalesReturnItemRepository extends JpaRepository<SalesReturnItem, String> {

    List<SalesReturnItem> findBySalesReturnId(String salesReturnId);

    Optional<SalesReturnItem> findBySalesReturnIdAndProductId(String salesReturnId, String productId);

    Optional<SalesReturnItem> findTopByOrderBySalesReturnItemIdDesc();

    // ✅ FIXED: Cast return_id to VARCHAR to match sales_return_id type
    @Query(value = "SELECT COALESCE(SUM(sri.quantity), 0) " +
            "FROM sales_return_items sri " +
            "JOIN sales_returns sr ON sri.sales_return_id = CAST(sr.return_id AS VARCHAR) " +
            "WHERE sri.product_id = :productId AND sr.sale_id = :saleId",
            nativeQuery = true)
    Integer getTotalReturnedQuantityForProductInSale(@Param("productId") String productId,
                                                     @Param("saleId") String saleId);
}