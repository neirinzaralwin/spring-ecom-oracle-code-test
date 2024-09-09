package com.neirinzaralwin.spring_ecommerce.service;

import com.neirinzaralwin.spring_ecommerce.entity.CartProduct;
import com.neirinzaralwin.spring_ecommerce.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductService {

    @Autowired
    private CartProductRepository cartProductRepository;

    public CartProduct getCartProductById(int id){
        return cartProductRepository.findById(id).orElse(null);
    }

}