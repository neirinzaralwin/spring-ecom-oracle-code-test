package com.neirinzaralwin.spring_ecommerce.controller;

import com.neirinzaralwin.spring_ecommerce.entity.Product;
import com.neirinzaralwin.spring_ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<Object> findAllProducts() {
        List<Product> products = productService.getProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("count", products.size());
        response.put("data", products);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Map<String, Object> response = new HashMap<>();
        Product newProduct = productService.saveProduct(product);

        response.put("message", "Product created successfully");
        response.put("data", newProduct);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();

        // Fetch the previous product
        Product previousProduct = productService.getProductById(product.getProductId());
        if (previousProduct == null) {
            response.put("error", "Product not found with this id.");
            return ResponseEntity.badRequest().body(response);
        }

        // Preserve immutable fields
        product.setSku(previousProduct.getSku());
        product.setCreatedAt(previousProduct.getCreatedAt());
        product.setUpdatedAt(LocalDateTime.now());

        // Update the product
        Product updatedProduct = productService.saveProduct(product);
        response.put("message", "Product updated successfully");
        response.put("data", updatedProduct);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteProduct(@RequestParam Integer id){
        Map<String, String> response = new HashMap<>();

        if(id != null){
            productService.deleteProduct(id);
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok().body(response);
        } else {
            response.put("error", "Please provide product id");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
