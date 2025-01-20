package com.retail.product_catalog.service;

import com.retail.product_catalog.model.Product;
import com.retail.product_catalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final FuzzySearchService fuzzySearchService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, FuzzySearchService fuzzySearchService) {
        this.productRepository = productRepository;
        this.fuzzySearchService = fuzzySearchService;
    }

    public Product addProduct(Product product){
        return productRepository.saveProduct(product);
    }

    public Optional<Product> getProduct(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllProducts();
        }

        return productRepository.findAll().stream()
                .filter(product -> fuzzySearchService.isSimilar(product.getName(), query))
                .collect(Collectors.toList());
    }
}
