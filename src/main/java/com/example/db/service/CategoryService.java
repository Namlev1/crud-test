package com.example.db.service;

import com.example.db.model.Category;
import com.example.db.model.converters.CategoryConverter;
import com.example.db.model.dto.CategoryDto;
import com.example.db.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDto save(CategoryDto dto) {
        Category category = CategoryConverter.toCategory(dto);
        category = categoryRepository.save(category);
        return CategoryConverter.toDto(category);
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryConverter::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(CategoryConverter::toDto).orElse(null);
    }

    public CategoryDto findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.map(CategoryConverter::toDto).orElse(null);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
