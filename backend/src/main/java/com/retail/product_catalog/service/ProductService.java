package com.retail.product_catalog.service;

import com.retail.product_catalog.exception.InvalidSearchParameterException;
import com.retail.product_catalog.exception.ProductNotFoundException;
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

    /**
     * Adds a new product to the catalog.
     *
     * @param product the product to add.
     * @return the added product.
     */
    public Product addProduct(Product product){
        return productRepository.saveProduct(product);
    }

    /**
     * Retrieves a product from the catalog by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return an Optional containing the product if found, otherwise an empty Optional
     */
    public Optional<Product> getProduct(Long id){
        return Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    /**
     * Retrieves all products from the catalog.
     *
     * @return a list of all products in the catalog
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Searches for products based on a query string. The search is case-insensitive and uses fuzzy matching
     * to find similar product names.
     *
     * @param query the query string to search for
     * @return a list of products whose names are similar to the query
     */
    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new InvalidSearchParameterException("Search query cannot be empty");
        }

        return productRepository.findAll().stream()
                .filter(product -> fuzzySearchService.isSimilar(product.getName(), query))
                .collect(Collectors.toList());
    }
}
