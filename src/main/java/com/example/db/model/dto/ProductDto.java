package com.example.db.model.dto;

import java.util.List;

public record ProductDto(
        Integer id,
        String name,
        String description,
        double price,
        ProductDetailsDto details,
        CategoryDto category
) {
}
