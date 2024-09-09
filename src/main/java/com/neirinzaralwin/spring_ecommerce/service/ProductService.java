package com.neirinzaralwin.spring_ecommerce.service;

import com.neirinzaralwin.spring_ecommerce.entity.Product;
import com.neirinzaralwin.spring_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts () {
        return repository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return repository.findById(id);
    }

    public Product getProductByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    public Product getProductBySku(String sku) {
        return repository.findBySku(sku).orElse(null);
    }

    public String deleteProduct(int id) {
        repository.deleteById(id);
        return "Product removed !! " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getProductId())
                .orElseThrow(() -> new BadCredentialsException("No product found with this id"));
        if (existingProduct == null) return null;
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSku(product.getSku());
        return repository.save(existingProduct);
    }
}
