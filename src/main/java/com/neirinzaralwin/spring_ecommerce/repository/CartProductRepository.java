package com.neirinzaralwin.spring_ecommerce.repository;

import com.neirinzaralwin.spring_ecommerce.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct,Integer> {


}