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

        productService.deleteById(1);


        List<ProductDto> products = productService.findAll();


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

        CategoryDto categoryDto = new CategoryDto("name", null);
        ProductDetailsDto detailsDto = new ProductDetailsDto(null, "manufacturer", "none");
        ProductDto productDto = new ProductDto( null, "testProduct", "testproductDesc", 12, detailsDto, categoryDto);

        ProductDto saved = productService.save(productDto);
        assertNotNull(saved);
        assertEquals("testProduct", saved.name());

    }

    @Test
    void save_validInputWithNewCategory() {

        ProductDto dto = new ProductDto(
                null,
                "New Product",
                "Description",
                99.99,
                new ProductDetailsDto(null, "Manufacturer", "Warranty"),
                new CategoryDto("New Category", null)
        );


        ProductDto result = productService.save(dto);


        assertNotNull(result);
        assertEquals("New Product", result.name());
        assertEquals("New Category", result.category().name());
    }


    @Test
    void save_validProductWithExistingCategory() {

        ProductDto dto = new ProductDto(
                null,
                "New Product",
                "Description",
                99.99,
                new ProductDetailsDto(null, "Manufacturer", "Warranty"),
                new CategoryDto(null, 1L) // ID istniejącej kategorii
        );


        ProductDto result = productService.save(dto);


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

        ProductDto dto = new ProductDto(
                1,
                "Updated Product",
                "Updated Description",
                99.99,
                new ProductDetailsDto(null, "Updated Manufacturer", "2 years"),
                new CategoryDto("Updated Category", null)
        );


        ProductDto updatedProduct = productService.update(dto);


        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.name());
        assertEquals("Updated Description", updatedProduct.description());
        assertEquals(99.99, updatedProduct.price());
        assertEquals("Updated Manufacturer", updatedProduct.details().manufacturer());
        assertEquals("Updated Category", updatedProduct.category().name());
    }


    @Test
    void update_validProduct_with_Existing_Category() {

        ProductDto dto = new ProductDto(
                1,
                "Updated Product",
                "Updated Description",
                99.99,
                new ProductDetailsDto(null, "Updated Manufacturer", "2 years"),
                new CategoryDto(null, 2L)
        );


        ProductDto updatedProduct = productService.update(dto);


        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.name());
        assertEquals("Updated Description", updatedProduct.description());
        assertEquals(99.99, updatedProduct.price());
        assertEquals("Updated Manufacturer", updatedProduct.details().manufacturer());
        assertEquals("p2 - category", updatedProduct.category().name());
    }

    @Test
    void update_invalidDto_returnsNull() {

        ProductDto dto = new ProductDto(
                null,
                null,
                "Description",
                99.99,
                new ProductDetailsDto(null, "Manufacturer", "Warranty"),
                new CategoryDto("Category", null)
        );


        ProductDto result = productService.update(dto);


        assertNull(result);
    }

    @Test
    void update_nonExistentProduct_throwsException() {

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
