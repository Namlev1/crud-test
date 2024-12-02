package com.example.db.controllers;

import com.example.db.model.dto.ProductDto;
import com.example.db.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/", consumes = {"application/json"})
    public ProductDto save(@RequestBody ProductDto dto) {
        return productService.save(dto);
    }

    @PutMapping(value = "/", consumes = {"application/json"})
    public ProductDto update(@RequestBody ProductDto dto) {
        return productService.update(dto);
    }

    @GetMapping("/all")
    public List<ProductDto> all() {
        return productService.findAll();
    }

    @GetMapping("/id/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Integer id) {
        productService.deleteById(id);
    }

    @PostMapping("/sample")
    public List<ProductDto> save() {
        return productService.addSampleProducts();
    }
}
