package com.example.db.controllers;

import com.example.db.model.dto.CategoryDto;
import com.example.db.service.CategoryService;
import com.example.db.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @PostMapping("/categories")
    public String createCategory(@ModelAttribute CategoryDto categoryDto) {
        categoryService.save(categoryDto);
        return "redirect:/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "edit-category";
    }

    @PostMapping("/categories/edit")
    public String updateCategory(@ModelAttribute CategoryDto categoryDto) {
        categoryService.update(categoryDto);
        return "redirect:/categories";
    }
}
