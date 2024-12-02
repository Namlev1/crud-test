package com.example.db.model.converters;

import com.example.db.model.Category;
import com.example.db.model.dto.CategoryDto;

public class CategoryConverter {
    public static Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.name())
                .id(categoryDto.id())
                .build();
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getName(), category.getId());
    }
}
