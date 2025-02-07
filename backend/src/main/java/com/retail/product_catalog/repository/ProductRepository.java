package com.retail.product_catalog.repository;

import com.retail.product_catalog.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private long currentId = 1;

    public ProductRepository() {
        //mock products
        initializeMockData();
    }

    private void initializeMockData() {
        Product iphone = new Product();
        iphone.setName("iPhone 13");
        iphone.setCategory("Electronics");
        iphone.setDescription("Latest iPhone model with advanced features");
        iphone.setPrice(999.99);
        iphone.setImageUrl("http://localhost:8080/images/iphone13.jpg"); //imageUrl to be displayed in the frontend
        saveProduct(iphone);

        Product tv = new Product();
        tv.setName("Samsung TV");
        tv.setCategory("Electronics");
        tv.setDescription("4K Smart TV with HDR");
        tv.setPrice(799.99);
        tv.setImageUrl("http://localhost:8080/images/samsung-tv.jpg");
        saveProduct(tv);

        Product coffee = new Product();
        coffee.setName("Coffee Maker");
        coffee.setCategory("Appliances");
        coffee.setDescription("Programmable coffee maker with timer");
        coffee.setPrice(49.99);
        coffee.setImageUrl("http://localhost:8080/images/coffee-maker.jpg");
        saveProduct(coffee);
    }

    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            product.setId(currentId++);
        }
        products.put(product.getId(), product);
        return product;
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }
}
