package com.neirinzaralwin.spring_ecommerce.controller;
import com.neirinzaralwin.spring_ecommerce.entity.Cart;
import com.neirinzaralwin.spring_ecommerce.repository.CartRepository;
import com.neirinzaralwin.spring_ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/carts")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public ResponseEntity<Object> findAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        Map<String, Object> response = new HashMap<>();
        response.put("data", carts);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCart(@RequestBody Cart cart) {
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        Map<String, Object> response = new HashMap<>();
        Cart newCart = cartService.saveCart(cart);
        response.put("message", "Cart created successfully");
        response.put("data", newCart);
        return ResponseEntity.ok().body(response);
    }

}