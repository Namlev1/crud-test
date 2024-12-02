package com.example.db.service;

import com.example.db.model.Category;
import com.example.db.model.Product;
import com.example.db.model.ProductDetails;
import com.example.db.model.Tag;
import com.example.db.model.converters.CategoryConverter;
import com.example.db.model.converters.ProductConverter;
import com.example.db.model.converters.ProductDetailsConverter;
import com.example.db.model.converters.TagConverter;
import com.example.db.model.dto.ProductDto;
import com.example.db.repository.CategoryRepository;
import com.example.db.repository.ProductDetailsRepository;
import com.example.db.repository.ProductRepository;
import com.example.db.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
            category = CategoryConverter.toCategory(dto.category());
        }
        product.setCategory(category);

        if (dto.tags() != null) {
            List<Tag> tags = dto.tags().stream()
                    .map(tagDto -> {
                        Tag tag;
                        if (tagDto.id() != null) {
                            tag = tagRepository.findById(tagDto.id()).orElseThrow();
                        } else {
                            tag = TagConverter.toTag(tagDto);
                        }
                        return tag;
                    })
                    .collect(Collectors.toList());
            product.setTags(tags);
        }

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
        } else {
            details = ProductDetailsConverter.toDetails(dto.details());
        }
        product.setDetails(details);

        Category category;
        if (dto.category().id() != null) {
            category = categoryRepository.findById(dto.category().id()).orElseThrow();
        } else {
            category = CategoryConverter.toCategory(dto.category());
            category = categoryRepository.save(category);
        }
        product.setCategory(category);

        List<Tag> tags = dto.tags().stream()
                .map(tagDto -> {
                    Tag tag;
                    if (tagDto.id() != null) {
                        tag = tagRepository.findById(tagDto.id()).orElseThrow();
                    } else {
                        tag = TagConverter.toTag(tagDto);
                    }
                    return tag;
                })
                .collect(Collectors.toList());
        product.setTags(tags);

        product = productRepository.save(product);
        return ProductConverter.toDto(product);
    }

    private static boolean isDtoInvalid(ProductDto dto) {
        return dto.id() == null || dto.category() == null || dto.tags() == null || dto.details() == null;
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

    public List<ProductDto> addSampleProducts() {
        Category category1 = Category.builder().name("p1 - category").build();
        Category category2 = Category.builder().name("p2 - category").build();

        ProductDetails details1 = ProductDetails.builder()
                .manufacturer("p1 - manufacturer")
                .warranty("p1 - warranty")
                .build();

        ProductDetails details2 = ProductDetails.builder()
                .manufacturer("p2 - manufacturer")
                .warranty("p2 - warranty")
                .build();

        Tag tag1 = Tag.builder().name("tag1").build();
        Tag tag2 = Tag.builder().name("tag2").build();

        category1 = categoryRepository.save(category1);
        category2 = categoryRepository.save(category2);

        details1 = productDetailsRepository.save(details1);
        details2 = productDetailsRepository.save(details2);

        tag1 = tagRepository.save(tag1);
        tag2 = tagRepository.save(tag2);

        Product product1 = new Product();
        product1.setName("p1");
        product1.setDescription("p1 Description");
        product1.setPrice(15.0);
        product1.setCategory(category1);
        product1.setDetails(details1);
        product1.setTags(Arrays.asList(tag1, tag2));

        Product product2 = new Product();
        product2.setName("p2");
        product2.setDescription("p2 Description");
        product2.setPrice(35.0);
        product2.setCategory(category2);
        product2.setDetails(details2);
        product2.setTags(Arrays.asList(tag1, tag2));

        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        return List.of(ProductConverter.toDto(product1), ProductConverter.toDto(product2));
    }
}
