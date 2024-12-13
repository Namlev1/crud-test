package com.example.db.model.converters;

import com.example.db.model.Category;
import com.example.db.model.Product;
import com.example.db.model.ProductDetails;
import com.example.db.model.dto.CategoryDto;
import com.example.db.model.dto.ProductDetailsDto;
import com.example.db.model.dto.ProductDto;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class ProductConverterTest {

    @Test
    void toProduct_validDto_returnsProduct() {
        //Given
        ProductDto dto = new ProductDto(
                1,
                "Test Product",
                "Description",
                99.99,
                new ProductDetailsDto(1L, "Manufacturer", "Warranty"),
                new CategoryDto("Test Category", 1L)
        );

        //When
        Product product = ProductConverter.toProduct(dto);

        //Then
        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Manufacturer", product.getDetails().getManufacturer());
        assertEquals("Test Category", product.getCategory().getName());
    }

    @Test
    void toProduct_nullDto_throwsException() {
        //Given
        ProductDto dto = null;

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductConverter.toProduct(dto)
        );
        //Then
        assertEquals("ProductDto cannot be null", exception.getMessage());
    }

    @Test
    void toProduct_nullDetails_throwsException() {
        //Given
        ProductDto dto = new ProductDto(
                1,
                "Test Product",
                "Description",
                99.99,
                null,
                new CategoryDto("Test Ctaegory", 1L)
        );

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductConverter.toProduct(dto)
        );
        //Then
        assertEquals("ProductDetails cannot be null in ProductDto", exception.getMessage());
    }

    @Test
    void toProduct_nullCategory_throwsException() {
        //Given
        ProductDto dto = new ProductDto(
                1,
                "Test Product",
                "Description",
                99.99,
                new ProductDetailsDto(1L, "Manufacturer", "Warranty"),
                null
        );

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductConverter.toProduct(dto)
        );
        //Then
        assertEquals("Category cannot be null in ProductDto", exception.getMessage());
    }


    @Test
    void toDto_validProduct_returnsDto() {
        //Given
        Product product = Product.builder()
                .id(1)
                .name("Test Product")
                .description("Description")
                .price(99.99)
                .details(ProductDetails.builder().id(1L).manufacturer("testManufacturer").warranty("none").build())
                .category(Category.builder()
                        .name("Test Category")
                        .id(3L)
                        .build())
                .build();

        //When
        ProductDto dto = ProductConverter.toDto(product);

        //Then
        assertNotNull(dto);
        assertEquals(1, dto.id());
        assertEquals("Test Product", dto.name());
        assertEquals("testManufacturer", dto.details().manufacturer());
        assertEquals("Test Category", dto.category().name());
    }

    @Test
    void toDto_nullProduct_throwsException() {
        //Given
        Product product = null;

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductConverter.toDto(product)
        );
        //Then
        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    void toDto_nullDetails_throwsException() {
        //Given
        Product product = Product.builder()
                .id(1)
                .name("Test Product")
                .description("Description")
                .price(99.99)
                .details(null)
                .category(Category.builder()
                        .name("Test Category")
                        .id(3L)
                        .build())
                .build();

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductConverter.toDto(product)
        );
        //Then
        assertEquals("ProductDetails cannot be null in Product", exception.getMessage());
    }

    @Test
    void toDto_nullCategory_throwsException() {
        //Given
        Product product = Product.builder()
                .id(1)
                .name("Test Product")
                .description("Description")
                .price(99.99)
                .details(ProductDetails.builder().id(1L).manufacturer("testManufacturer").warranty("none").build())
                .category(null)
                .build();

        //When
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductConverter.toDto(product)
        );
        //Then
        assertEquals("Category cannot be null in Product", exception.getMessage());
    }


}
