package com.example.db.model.converters;

import com.example.db.model.ProductDetails;
import com.example.db.model.dto.ProductDetailsDto;

public class ProductDetailsConverter {
    public static ProductDetails toDetails(ProductDetailsDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ProductDetailsDto cannot be null");
        }

        return ProductDetails.builder()
                .id(dto.id())
                .manufacturer(dto.manufacturer())
                .warranty(dto.warranty())
                .build();
    }

    public static ProductDetailsDto toDto(ProductDetails details) {
        if (details == null) {
            throw new IllegalArgumentException("ProductDetails cannot be null");
        }

        return new ProductDetailsDto(
                details.getId(),
                details.getManufacturer(),
                details.getWarranty()
        );
    }
}
