package com.retail.product_catalog.service;

import com.retail.product_catalog.exception.InvalidSearchParameterException;
import com.retail.product_catalog.exception.ProductNotFoundException;
import com.retail.product_catalog.model.Product;
import com.retail.product_catalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private FuzzySearchService fuzzySearchService;
    private ProductService productService;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, fuzzySearchService);
        testProduct = new Product(
                1L,
                "Test_Product",
                "Test_Category",
                "Test_Description",
                99.99,
                "test.jpg"
        );
    }

    @Nested
    @DisplayName("Add Product Tests")
    class AddProductTests {

        @Test
        @DisplayName("Test should successfully add a valid product")
        void shouldAddValidProduct() {
            when(productRepository.saveProduct(testProduct)).thenReturn(testProduct);

            Product result = productService.addProduct(testProduct);

            assertThat(result).isNotNull()
                    .isEqualTo(testProduct);
            verify(productRepository).saveProduct(testProduct);
        }
    }

    @Nested
    @DisplayName("Get Product Tests")
    class GetProductTests {

        @Test
        @DisplayName("Test should return product when found by ID")
        void shouldReturnProductWhenFound() {
            when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

            Optional<Product> result = productService.getProduct(1L);

            assertThat(result)
                    .isPresent()
                    .hasValueSatisfying(product -> {
                        assertThat(product.getId()).isEqualTo(testProduct.getId());
                        assertThat(product.getName()).isEqualTo(testProduct.getName());
                        assertThat(product.getCategory()).isEqualTo(testProduct.getCategory());
                        assertThat(product.getDescription()).isEqualTo(testProduct.getDescription());
                        assertThat(product.getPrice()).isEqualTo(testProduct.getPrice());
                        assertThat(product.getImageUrl()).isEqualTo(testProduct.getImageUrl());
                    });
        }

        @Test
        @DisplayName("Test should throw ProductNotFoundException when product not found")
        void shouldThrowExceptionWhenProductNotFound() {
            when(productRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productService.getProduct(1L))
                    .isInstanceOf(ProductNotFoundException.class)
                    .hasMessageContaining("Product not found with id: 1");
        }
    }

    @Nested
    @DisplayName("Get All Products Tests")
    class GetAllProductsTests {

        @Test
        @DisplayName("Test should return all products")
        void shouldReturnAllProducts() {
            List<Product> products = Collections.singletonList(testProduct);
            when(productRepository.findAll()).thenReturn(products);

            List<Product> result = productService.getAllProducts();

            assertThat(result)
                    .isNotEmpty()
                    .hasSize(1);
            assertThat(result.getFirst().getId()).isEqualTo(testProduct.getId());
            assertThat(result.getFirst().getName()).isEqualTo(testProduct.getName());
        }

        @Test
        @DisplayName("Test should return empty list when no products exist")
        void shouldReturnEmptyListWhenNoProducts() {
            when(productRepository.findAll()).thenReturn(List.of());

            List<Product> result = productService.getAllProducts();

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("Search Products Tests")
    class SearchProductsTests {

        @Test
        @DisplayName("Test should return matching products when search query is valid")
        void shouldReturnMatchingProducts() {
            List<Product> products = Collections.singletonList(testProduct);
            when(productRepository.findAll()).thenReturn(products);
            when(fuzzySearchService.isSimilar(anyString(), anyString())).thenReturn(true);

            List<Product> result = productService.searchProducts("test");

            assertThat(result)
                    .isNotEmpty()
                    .hasSize(1);
            assertThat(result.getFirst().getName()).isEqualTo(testProduct.getName());
            verify(fuzzySearchService).isSimilar(testProduct.getName(), "test");
        }

        @Test
        @DisplayName("Test should return empty list when no matches found")
        void shouldReturnEmptyListWhenNoMatches() {
            List<Product> products = Collections.singletonList(testProduct);
            when(productRepository.findAll()).thenReturn(products);
            when(fuzzySearchService.isSimilar(anyString(), anyString())).thenReturn(false);

            List<Product> result = productService.searchProducts("nonexistent");

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Test should throw exception when search query is null")
        void shouldThrowExceptionWhenQueryIsNull() {
            assertThatThrownBy(() -> productService.searchProducts(null))
                    .isInstanceOf(InvalidSearchParameterException.class)
                    .hasMessageContaining("Search query cannot be empty");
        }

        @Test
        @DisplayName("Test should throw exception when search query is empty")
        void shouldThrowExceptionWhenQueryIsEmpty() {
            assertThatThrownBy(() -> productService.searchProducts("  "))
                    .isInstanceOf(InvalidSearchParameterException.class)
                    .hasMessageContaining("Search query cannot be empty");
        }
    }
}
