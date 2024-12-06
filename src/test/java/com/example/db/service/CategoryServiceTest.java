package com.example.db.service;

import com.example.db.model.dto.CategoryDto;
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
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void findAll() {
        List<CategoryDto> categories = categoryService.findAll();
        assertEquals(2, categories.size());
        assertEquals("p1 - category", categories.getFirst().name());
        assertEquals("p2 - category", categories.get(1).name());
    }

    @Test
    void findById() {
        CategoryDto category = categoryService.findById(1L);
        assertNotNull(category);
        assertEquals(1L, category.id());
        assertEquals("p1 - category", category.name());
    }

    @Test
    void findById_invalidId() {
        CategoryDto category = categoryService.findById(3L);
        assertNull(category);
    }

    @Test
    void findById_nullInput() {
        InvalidDataAccessApiUsageException exception = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> categoryService.findById(null),
                "Expected findById(null) to throw InvalidDataAccessApiUsageException"
        );

        // Verify the exception message
        assertEquals("The given id must not be null", exception.getMessage());
    }

    @Test
    void deleteById() {
        categoryService.deleteById(2L);
        List<CategoryDto> categories = categoryService.findAll();
        assertEquals(1, categories.size());
    }

    @Test
    void deleteById_cannotDeleteBecauseProductIsAssigned() {
        categoryService.deleteById(1L);
        List<CategoryDto> categories = categoryService.findAll();
        assertEquals(2, categories.size());
    }

    @Test
    void deleteById_invalidInput() {
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> categoryService.deleteById(3L),
                "Expected categoryService.deleteById(3L) to throw NoSuchElementException"
        );

        // Verify the exception message
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    void deleteById_nullInput() {
        InvalidDataAccessApiUsageException exception = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> categoryService.deleteById(null),
                "Expected categoryService(null) to throw InvalidDataAccessApiUsageException"
        );

        // Verify the exception message
        assertEquals("The given id must not be null", exception.getMessage());
    }

    @Test
    void save() {
        CategoryDto dto = new CategoryDto("name", null);
        CategoryDto saved = categoryService.save(dto);

        assertNotNull(saved);
        assertEquals("name", saved.name());
    }

    @Test
    void save_nullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.save(null),
                "Expected categoryService.save(null) to throw IllegalArgumentException"
        );

        // Verify the exception message
        assertEquals("categoryDto cannot be null", exception.getMessage());
    }

    @Test
    void save_emptyName() {
        CategoryDto dto = new CategoryDto("", null);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.save(dto),
                "Expected categoryService(null) to throw IllegalArgumentException"
        );

        // Verify the exception message
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    void update() {
        CategoryDto dto = new CategoryDto("updated", 1L);
        categoryService.save(dto);
        CategoryDto saved = categoryService.findById(1L);

        assertEquals("updated", saved.name());
    }

    @Test
    void update_nullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.update(null),
                "Expected categoryService.save(null) to throw IllegalArgumentException"
        );

        // Verify the exception message
        assertEquals("categoryDto cannot be null", exception.getMessage());
    }

    @Test
    void update_emptyName() {
        CategoryDto dto = new CategoryDto("", 1L);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.save(dto),
                "Expected categoryService(null) to throw IllegalArgumentException"
        );

        // Verify the exception message
        assertEquals("Name cannot be empty", exception.getMessage());

        CategoryDto saved = categoryService.findById(1L);
        assertEquals("p1 - category", saved.name());
    }
}
