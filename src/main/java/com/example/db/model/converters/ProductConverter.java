package com.example.db.model.converters;

import com.example.db.model.Product;
import com.example.db.model.dto.ProductDto;

public class ProductConverter {
    public static Product toProduct(ProductDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ProductDto cannot be null");
        }
        if (dto.details() == null) {
            throw new IllegalArgumentException("ProductDetails cannot be null in ProductDto");
        }
        if (dto.category() == null) {
            throw new IllegalArgumentException("Category cannot be null in ProductDto");
        }

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
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getDetails() == null) {
            throw new IllegalArgumentException("ProductDetails cannot be null in Product");
        }
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null in Product");
        }

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

