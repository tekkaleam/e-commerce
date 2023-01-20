package com.shop.ecommerse.repository.product;

import com.shop.ecommerse.domain.Order;
import com.shop.ecommerse.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryFunc{
    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        if(!product.isNew()) {
            return null;
        }
        return productRepository.save(product);
    }

    public ProductRepositoryImpl(@Lazy ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public int delete(Long id) {
        return productRepository.delete(id);
    }

    @Override
    public Optional<Product> findByProductName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findProductsBetweenPrice(Integer bottomPrice, Integer roofPrice) {
        return productRepository.findProductsBetweenPrice(bottomPrice, roofPrice);
    }
}
