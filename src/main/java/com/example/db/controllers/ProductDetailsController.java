package com.example.db.controllers;

import com.example.db.model.dto.ProductDetailsDto;
import com.example.db.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/details")
public class ProductDetailsController {
    private final ProductDetailsService service;

    @PostMapping(value = "/", consumes = {"application/json"})
    public ProductDetailsDto save(@RequestBody ProductDetailsDto dto) {
        return service.save(dto);
    }

    @GetMapping("/all")
    public List<ProductDetailsDto> getAllDetails() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public ProductDetailsDto getDetailsById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/warranty/{warranty}")
    public ProductDetailsDto getByWarranty(@PathVariable String warranty) {
        return service.findByWarranty(warranty);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public ProductDetailsDto getByManufacturer(@PathVariable String manufacturer) {
        return service.findByManufacturer(manufacturer);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
