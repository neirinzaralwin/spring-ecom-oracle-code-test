package com.neirinzaralwin.spring_ecommerce.repository;

import com.neirinzaralwin.spring_ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Optional<Product> findByName(String name);

    Optional<Product> findBySku(String sku);
}