package com.example.db.model.converters;

import com.example.db.model.ProductDetails;
import com.example.db.model.dto.ProductDetailsDto;

public class ProductDetailsConverter {
    public static ProductDetails toDetails(ProductDetailsDto dto) {
        return ProductDetails.builder()
                .id(dto.id())
                .manufacturer(dto.manufacturer())
                .warranty(dto.warranty())
                .build();
    }

    public static ProductDetailsDto toDto(ProductDetails tag) {
        return new ProductDetailsDto(tag.getId(), tag.getManufacturer(), tag.getWarranty());
    }
}
