package io.oferto.application.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.oferto.application.backend.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("SELECT s FROM Stock s WHERE s.product.warehouse.id = :warehouseId")
    Optional<List<Stock>> findAllByWarehouseId(@Param("warehouseId") Long warehouseId);
}
