package com.neirinzaralwin.spring_ecommerce.repository;

import com.neirinzaralwin.spring_ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    Optional<Cart> findByUser_UserId(int userId);

}