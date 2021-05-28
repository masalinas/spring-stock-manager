package io.oferto.application.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.oferto.application.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p WHERE p.warehouse.id = :productId")
    Optional<List<Product>> findAllByWarehouseId(@Param("productId") Long productId);
}
