package com.example.db.model.converters;

import com.example.db.model.Product;
import com.example.db.model.dto.ProductDto;

public class ProductConverter {
    public static Product toProduct(ProductDto dto) {
        return Product.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .details(ProductDetailsConverter.toDetails(dto.details()))
                .category(CategoryConverter.toCategory(dto.category()))
                .build();
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                ProductDetailsConverter.toDto(product.getDetails()),
                CategoryConverter.toDto(product.getCategory())
        );
    }
}
