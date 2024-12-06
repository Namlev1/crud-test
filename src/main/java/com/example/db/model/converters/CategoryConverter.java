package com.example.db.model.converters;

import com.example.db.model.Category;
import com.example.db.model.dto.CategoryDto;

public class CategoryConverter {
    public static Category toCategory(CategoryDto categoryDto){
        if (categoryDto == null) {
            throw new IllegalArgumentException("categoryDto cannot be null");
        }
        return Category.builder()
                .name(categoryDto.name())
                .id(categoryDto.id())
                .build();
    }

    public static CategoryDto toDto(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        return new CategoryDto(category.getName(), category.getId());
    }
}
