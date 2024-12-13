package com.example.db.model.converters;


import com.example.db.model.Category;
import com.example.db.model.Product;
import com.example.db.model.ProductDetails;
import com.example.db.model.dto.CategoryDto;
import com.example.db.model.dto.ProductDetailsDto;
import com.example.db.model.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
public class ProductDetailsConverterTest {
    @Test
    void toDetails_validDto_returnsDetails() {
        //Given
        ProductDetailsDto dto = new ProductDetailsDto(1L, "Manufacturer", "2 years");

        //When
        ProductDetails details = ProductDetailsConverter.toDetails(dto);

        //Then
        assertNotNull(details);
        assertEquals(1, details.getId());
        assertEquals("Manufacturer", details.getManufacturer());
        assertEquals("2 years", details.getWarranty());
    }

    @Test
    void toDetails_nullDto_throwsException() {
        //Given
        ProductDetailsDto dto = null;

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductDetailsConverter.toDetails(dto)
        );
        //Then
        assertEquals("ProductDetailsDto cannot be null", exception.getMessage());
    }

    @Test
    void toDto_validDetails_returnsDto() {
        //Given
        ProductDetails details = ProductDetails.builder()
                .id(1L)
                .manufacturer("Manufacturer")
                .warranty("2 years")
                .build();

        //When
        ProductDetailsDto dto = ProductDetailsConverter.toDto(details);

        //Then
        assertNotNull(dto);
        assertEquals(1, dto.id());
        assertEquals("Manufacturer", dto.manufacturer());
        assertEquals("2 years", dto.warranty());
    }

    @Test
    void toDto_nullDetails_throwsException() {
        //Given
        ProductDetails details = null;

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductDetailsConverter.toDto(details)
        );
        //Then
        assertEquals("ProductDetails cannot be null", exception.getMessage());
    }
}
