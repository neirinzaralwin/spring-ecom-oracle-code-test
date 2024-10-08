package com.neirinzaralwin.spring_ecommerce.service;

import com.neirinzaralwin.spring_ecommerce.entity.Cart;
import com.neirinzaralwin.spring_ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart saveCart(Cart cart){
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartByUserId(int userId){
        return cartRepository.findByUser_UserId(userId);
    }

    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    // update cart
    public Cart updateCart(Cart cart){
        Cart existingCart = cartRepository.findById(cart.getCartId())
                .orElseThrow(() -> new BadCredentialsException("No cart found with this id"));
        if (existingCart == null) return null;
        existingCart.setItems(cart.getItems());
        return cartRepository.save(existingCart);
    }

    public String deleteCart(int id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return "Cart removed !!";
        } else {
            return "Cart not found !!";
        }
    }
}