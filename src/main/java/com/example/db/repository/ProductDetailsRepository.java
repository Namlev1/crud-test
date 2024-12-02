package com.example.db.repository;

import com.example.db.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
    Optional<ProductDetails> findByWarranty(String warranty);
    Optional<ProductDetails> findByManufacturer(String manufacturer);
}
