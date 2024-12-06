package com.example.db;

import com.example.db.model.Category;
import com.example.db.model.Product;
import com.example.db.model.ProductDetails;
import com.example.db.repository.CategoryRepository;
import com.example.db.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        Category category1 = Category.builder().name("p1 - category").build();
        Category category2 = Category.builder().name("p2 - category").build();

        ProductDetails details1 = ProductDetails.builder()
                .manufacturer("p1 - manufacturer")
                .warranty("p1 - warranty")
                .build();

        category1 = categoryRepository.save(category1);
        category2 = categoryRepository.save(category2);

        Product product1 = new Product();
        product1.setName("p1");
        product1.setDescription("p1 Description");
        product1.setPrice(15.0);
        product1.setCategory(category1);
        product1.setDetails(details1);

        product1 = productRepository.save(product1);
    }
}
