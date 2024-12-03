package com.example.db.controllers;

import com.example.db.model.dto.ProductDto;
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
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute ProductDto productDto) {
        productService.save(productDto);
        return "redirect:/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "edit-product";
    }

    @PostMapping("/products/edit")
    public String updateProduct(@ModelAttribute ProductDto productDto) {
        productService.update(productDto);
        return "redirect:/products";
    }
}
