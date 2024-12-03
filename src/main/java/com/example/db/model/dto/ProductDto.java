package com.example.db.model.dto;

public record ProductDto(
        Integer id,
        String name,
        String description,
        double price,
        ProductDetailsDto details,
        CategoryDto category
) {
}
