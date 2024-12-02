package com.example.db.model.converters;

import com.example.db.model.Product;
import com.example.db.model.dto.ProductDto;

import java.util.stream.Collectors;

public class ProductConverter {
    public static Product toProduct(ProductDto dto) {
        return Product.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .details(ProductDetailsConverter.toDetails(dto.details()))
                .category(CategoryConverter.toCategory(dto.category()))
                .tags(dto.tags().stream().map(TagConverter::toTag).collect(Collectors.toList()))
                .build();
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                ProductDetailsConverter.toDto(product.getDetails()),
                CategoryConverter.toDto(product.getCategory()),
                product.getTags().stream().map(TagConverter::toDto).collect(Collectors.toList())
        );
    }
}
