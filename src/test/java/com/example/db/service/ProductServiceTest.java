package com.example.db.service;
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

@SpringBootTest
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;


    @Test
    void findAll() {
        List<ProductDto> products = productService.findAll();
        assertEquals(1, products.size());
        assertEquals("p1", products.get(0).name());
    }

    @Test
    void findAll_emptyRepository_returnsEmptyList() {
        //Given
        productService.deleteById(1);

        //When
        List<ProductDto> products = productService.findAll();

        //Then
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
    @Test
    void findById() {
        ProductDto product = productService.findById(1);
        assertNotNull(product);
        assertEquals(1, product.id());
        assertEquals("p1", product.name());
    }

    @Test
    void findById_invalidId() {
        ProductDto product = productService.findById(-1);
        assertNull(product);
    }

    @Test
    void findById_nullInput() {

        InvalidDataAccessApiUsageException exception = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> productService.findById(null),
                "Expected findById(null) to throw InvalidDataAccessApiUsageException"
        );

        // Verify the exception message
        assertEquals("The given id must not be null", exception.getMessage());
    }

    @Test
    void deleteByValidId() {
        productService.deleteById(1);
        List<ProductDto> products = productService.findAll();
        assertEquals(0, products.size());
    }
    @Test
    void deleteById_invalidId() {
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.deleteById(-1),
                "Expected productService.deleteById(999) to throw NoSuchElementException"
        );

        // Verify the exception message
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    void deleteById_nullInput() {
        InvalidDataAccessApiUsageException exception = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> productService.deleteById(null),
                "Expected productService.deleteById(null) to throw InvalidDataAccessApiUsageException"
        );


        assertEquals("The given id must not be null", exception.getMessage());
    }

    @Test
    void save_ProductWithNewCategory(){
        //Given
        CategoryDto categoryDto = new CategoryDto("name", null);
        ProductDetailsDto detailsDto = new ProductDetailsDto(null, "manufacturer", "none");
        ProductDto productDto = new ProductDto( null, "testProduct", "testproductDesc", 12, detailsDto, categoryDto);
        //When
        ProductDto saved = productService.save(productDto);
        //Then
        assertNotNull(saved);
        assertEquals("testProduct", saved.name());

    }

    @Test
    void save_validInputWithNewCategory() {
        //Given
        ProductDto dto = new ProductDto(
                null,
                "New Product",
                "Description",
                99.99,
                new ProductDetailsDto(null, "Manufacturer", "Warranty"),
                new CategoryDto("New Category", null)
        );

        //When
        ProductDto result = productService.save(dto);

        //Then
        assertNotNull(result);
        assertEquals("New Product", result.name());
        assertEquals("New Category", result.category().name());
    }


    @Test
    void save_validProductWithExistingCategory() {
        //Given
        ProductDto dto = new ProductDto(
                null,
                "New Product",
                "Description",
                99.99,
                new ProductDetailsDto(null, "Manufacturer", "Warranty"),
                new CategoryDto(null, 1L) // ID istniejącej kategorii
        );

        //When
        ProductDto result = productService.save(dto);

        //Then
        assertNotNull(result);
        assertEquals(1, result.category().id());
    }
    @Test
    void save_nullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.save(null),
                "Expected productService.save(null) to throw IllegalArgumentException"
        );


        assertEquals("dto cannot be null", exception.getMessage());
    }

    @Test
    void save_emptyName() {
        ProductDetailsDto detailsDto = new ProductDetailsDto(null, "manufacturer", "none");
        ProductDto productDto = new ProductDto( null, "", "testproductDesc", 12, detailsDto, categoryService.findById(1L));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.save(productDto),
                "Expected productService.save(emptyNameDto) to throw IllegalArgumentException"
        );


        assertEquals("Name cannot be empty", exception.getMessage());
    }


    @Test
    void update_validProduct_with_NewCategory() {
        //Given
        ProductDto dto = new ProductDto(
                1,
                "Updated Product",
                "Updated Description",
                99.99,
                new ProductDetailsDto(null, "Updated Manufacturer", "2 years"),
                new CategoryDto("Updated Category", null)
        );

        //When
        ProductDto updatedProduct = productService.update(dto);

        //Then
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.name());
        assertEquals("Updated Description", updatedProduct.description());
        assertEquals(99.99, updatedProduct.price());
        assertEquals("Updated Manufacturer", updatedProduct.details().manufacturer());
        assertEquals("Updated Category", updatedProduct.category().name());
    }


    @Test
    void update_validProduct_with_Existing_Category() {
        //Given
        ProductDto dto = new ProductDto(
                1,
                "Updated Product",
                "Updated Description",
                99.99,
                new ProductDetailsDto(null, "Updated Manufacturer", "2 years"),
                new CategoryDto(null, 2L)
        );

        //When
        ProductDto updatedProduct = productService.update(dto);

        //Then
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.name());
        assertEquals("Updated Description", updatedProduct.description());
        assertEquals(99.99, updatedProduct.price());
        assertEquals("Updated Manufacturer", updatedProduct.details().manufacturer());
        assertEquals("p2 - category", updatedProduct.category().name());
    }

    @Test
    void update_invalidDto_returnsNull() {
        //Given
        ProductDto dto = new ProductDto(
                null,
                null,
                "Description",
                99.99,
                new ProductDetailsDto(null, "Manufacturer", "Warranty"),
                new CategoryDto("Category", null)
        );

        //When
        ProductDto result = productService.update(dto);

        //Then
        assertNull(result);
    }

    @Test
    void update_nonExistentProduct_throwsException() {
        //Given
        ProductDto dto = new ProductDto(
                999,
                "Product",
                "Description",
                99.99,
                new ProductDetailsDto(null, "Manufacturer", "Warranty"),
                new CategoryDto("Category", null)
        );


        assertThrows(NoSuchElementException.class, () -> productService.update(dto));
    }
}
