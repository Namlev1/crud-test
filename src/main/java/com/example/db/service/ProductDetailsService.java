package com.example.db.service;

import com.example.db.model.ProductDetails;
import com.example.db.model.converters.ProductDetailsConverter;
import com.example.db.model.dto.ProductDetailsDto;
import com.example.db.repository.ProductDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailsService {
    private final ProductDetailsRepository repository;

    public ProductDetailsDto save(ProductDetailsDto dto) {
        ProductDetails details = ProductDetailsConverter.toDetails(dto);
        details = repository.save(details);
        return ProductDetailsConverter.toDto(details);
    }

    public List<ProductDetailsDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductDetailsConverter::toDto)
                .collect(Collectors.toList());
    }

    public ProductDetailsDto findById(Long id) {
        Optional<ProductDetails> details = repository.findById(id);
        return details.map(ProductDetailsConverter::toDto).orElse(null);
    }

    public ProductDetailsDto findByManufacturer(String manufacturer) {
        Optional<ProductDetails> details = repository.findByManufacturer(manufacturer);
        return details.map(ProductDetailsConverter::toDto).orElse(null);
    }

    public ProductDetailsDto findByWarranty(String warranty) {
        Optional<ProductDetails> details = repository.findByWarranty(warranty);
        return details.map(ProductDetailsConverter::toDto).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
