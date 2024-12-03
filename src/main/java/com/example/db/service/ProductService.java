package com.example.db.service;

import com.example.db.model.Category;
import com.example.db.model.Product;
import com.example.db.model.ProductDetails;
import com.example.db.model.converters.CategoryConverter;
import com.example.db.model.converters.ProductConverter;
import com.example.db.model.converters.ProductDetailsConverter;
import com.example.db.model.dto.ProductDto;
import com.example.db.repository.CategoryRepository;
import com.example.db.repository.ProductDetailsRepository;
import com.example.db.repository.ProductRepository;
import com.example.db.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProductDetailsRepository productDetailsRepository;

    public ProductDto save(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setDetails(ProductDetailsConverter.toDetails(dto.details()));

        Category category;
        if (dto.category().id() != null) {
            category = categoryRepository.findById(dto.category().id()).orElseThrow();
        } else {
            category = categoryRepository.save(CategoryConverter.toCategory(dto.category()));
        }
        product.setCategory(category);

//        if (dto.tags() != null) {
//            List<Tag> tags = dto.tags().stream()
//                    .map(tagDto -> {
//                        Tag tag;
//                        if (tagDto.id() != null) {
//                            tag = tagRepository.findById(tagDto.id()).orElseThrow();
//                        } else {
//                            tag = TagConverter.toTag(tagDto);
//                        }
//                        return tag;
//                    })
//                    .collect(Collectors.toList());
//            product.setTags(tags);
//        }

        product = productRepository.save(product);
        return ProductConverter.toDto(product);
    }

    public ProductDto update(ProductDto dto) {
        if (isDtoInvalid(dto)) {
            return null;
        }

        Product product = productRepository.findById(dto.id()).orElseThrow();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        ProductDetails details;

        if (dto.details().id() != null) {
            details = productDetailsRepository.findById(dto.details().id()).orElseThrow();
            details.setManufacturer(dto.details().manufacturer());
            details.setWarranty(dto.details().warranty());
        } else {
            details = ProductDetailsConverter.toDetails(dto.details());
        }
        product.setDetails(details);

        // todo
        Category category;
        if (dto.category().id() != null) {
            category = categoryRepository.findById(dto.category().id()).orElseThrow();
        } else {
            category = CategoryConverter.toCategory(dto.category());
            category = categoryRepository.save(category);
        }
        product.setCategory(category);

        product = productRepository.save(product);
        return ProductConverter.toDto(product);
    }

    private static boolean isDtoInvalid(ProductDto dto) {
        return dto.id() == null || dto.category() == null || dto.details() == null;
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ProductConverter::toDto).orElse(null);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}
