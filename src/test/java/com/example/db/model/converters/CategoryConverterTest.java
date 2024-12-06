package com.example.db.model.converters;

import com.example.db.model.Category;
import com.example.db.model.dto.CategoryDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryConverterTest {

    @Test
    void toCategory() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto("Electronics", 1L);

        // Act
        Category category = CategoryConverter.toCategory(categoryDto);

        // Assert
        assertNotNull(category);
        assertEquals(categoryDto.name(), category.getName());
        assertEquals(categoryDto.id(), category.getId());
    }

    @Test
    void toDto() {
        // Arrange
        Category category = Category.builder()
                .name("Books")
                .id(2L)
                .build();

        // Act
        CategoryDto categoryDto = CategoryConverter.toDto(category);

        // Assert
        assertNotNull(categoryDto);
        assertEquals(category.getName(), categoryDto.name());
        assertEquals(category.getId(), categoryDto.id());
    }

    @Test
    void toCategory_nullInput() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CategoryConverter.toCategory(null),
                "Expected toCategory(null) to throw IllegalArgumentException"
        );

        // Verify the exception message
        assertEquals("categoryDto cannot be null", exception.getMessage());
    }

    @Test
    void toDto_nullInput() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CategoryConverter.toDto(null),
                "Expected toCategory(null) to throw IllegalArgumentException"
        );

        // Verify the exception message
        assertEquals("category cannot be null", exception.getMessage());
    }
    
}
