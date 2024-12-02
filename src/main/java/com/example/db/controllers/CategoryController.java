package com.example.db.controllers;

import com.example.db.model.dto.CategoryDto;
import com.example.db.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping(value = "/")
    public CategoryDto save(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping("/all")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/id/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/name/{name}")
    public CategoryDto getByName(@PathVariable String name) {
        return categoryService.findByName(name);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
