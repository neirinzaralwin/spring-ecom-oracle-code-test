package com.neirinzaralwin.spring_ecommerce.controller;

import com.neirinzaralwin.spring_ecommerce.dto.LoginUserDto;
import com.neirinzaralwin.spring_ecommerce.dto.RegisterUserDto;
import com.neirinzaralwin.spring_ecommerce.entity.Cart;
import com.neirinzaralwin.spring_ecommerce.entity.User;
import com.neirinzaralwin.spring_ecommerce.response.LoginResponse;
import com.neirinzaralwin.spring_ecommerce.service.AuthenticationService;
import com.neirinzaralwin.spring_ecommerce.service.CartService;
import com.neirinzaralwin.spring_ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CartService cartService;


    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        // create cart
        Cart newCart = new Cart();
        newCart.setUser(registeredUser);
        newCart.setCreatedAt(LocalDateTime.now());
        newCart.setUpdatedAt(LocalDateTime.now());
        cartService.saveCart(newCart);

        // update register user
        registeredUser.setCart(newCart);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}