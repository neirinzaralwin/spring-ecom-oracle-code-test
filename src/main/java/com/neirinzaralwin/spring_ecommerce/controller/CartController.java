package com.neirinzaralwin.spring_ecommerce.controller;

import com.neirinzaralwin.spring_ecommerce.dto.AddToCartDto;
import com.neirinzaralwin.spring_ecommerce.dto.UpdateCartItemDto;
import com.neirinzaralwin.spring_ecommerce.entity.Cart;
import com.neirinzaralwin.spring_ecommerce.entity.CartProduct;
import com.neirinzaralwin.spring_ecommerce.entity.Product;
import com.neirinzaralwin.spring_ecommerce.entity.User;
import com.neirinzaralwin.spring_ecommerce.service.CartProductService;
import com.neirinzaralwin.spring_ecommerce.service.CartService;
import com.neirinzaralwin.spring_ecommerce.service.ProductService;
import com.neirinzaralwin.spring_ecommerce.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartProductService cartProductService;

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

    @GetMapping("/cart")
    public ResponseEntity<Object> findCartByUserId(@RequestParam Integer userId) {
        if (userId != null) {
            Cart cart = cartService.getCartByUserId(userId).orElse(null);
            return ResponseEntity.ok(cart);
        } else {
            Map<String, Object> body = new HashMap<>();
            body.put("error", "User id is required");
            return ResponseEntity.status(400).body(body);
        }
    }

    @PostMapping("/addToCart")
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartDto request) {
        Map<String, Object> response = new HashMap<>();

        // Fetch the user
        User user = userService.getUserById(request.getUserId());
        if (user == null) {
            response.put("error", "User not found with this id.");
            return ResponseEntity.badRequest().body(response);
        }

        // Fetch the product
        Product product = productService.getProductById(request.getProductId()).orElse(null);
        if (product == null) {
            response.put("error", "Product not found with this id.");
            return ResponseEntity.badRequest().body(response);
        }

        // Add product to cart
        try {
            Cart myCart = user.getCart();
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setQuantity(request.getQuantity());
            cartProduct.setCart(myCart);


            // check stock
            if (product.getStock() < request.getQuantity()) {
                response.put("error", "Product stock is not enough.");
                return ResponseEntity.badRequest().body(response);
            }else{
                product.setStock(product.getStock() - request.getQuantity());
                productService.saveProduct(product);
            }
            // add product to cart
            addCartProductToCart(myCart, cartProduct);

            cartService.updateCart(myCart);
            response.put("message", "Product added to cart successfully");
            response.put("cart", myCart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to add product to cart: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }


    private static void addCartProductToCart(Cart myCart, CartProduct cartProduct) {
        // check if the product is already in the cart
        for (CartProduct item : myCart.getItems()) {
            if (item.getProduct().getProductId() == cartProduct.getProduct().getProductId()) {
                item.setQuantity(item.getQuantity() + cartProduct.getQuantity());
                return;
            }
        }
        myCart.getItems().add(cartProduct);
    }

    @PostMapping("/removeAll")
    public ResponseEntity<Object> removeAllCartProducts(@RequestParam Integer userId) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.getUserById(userId);
        if (user == null) {
            response.put("error", "User not found with this id.");
            return ResponseEntity.badRequest().body(response);
        }
        Cart cart = user.getCart();
        cart.getItems().clear();
        Cart updatedCart = cartService.updateCart(cart);
        response.put("message", "All products removed from cart successfully");
        response.put("cart" , updatedCart);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateCartItem")
    public ResponseEntity<Object> updateCartItem(@RequestBody UpdateCartItemDto request){
        Map<String, Object> response = new HashMap<>();
        CartProduct cartProduct = cartProductService.getCartProductById(request.getCartProductId());
        if(cartProduct == null){
            response.put("error", "CartProduct not found with this id.");
            return ResponseEntity.badRequest().body(response);
        }

        // check stock
        if (cartProduct.getProduct().getStock() < request.getQuantity()) {
            response.put("error", "Product stock is not enough.");
            return ResponseEntity.badRequest().body(response);
        }else{
            cartProduct.getProduct().setStock(cartProduct.getProduct().getStock() - request.getQuantity());
            productService.saveProduct(cartProduct.getProduct());
        }

        cartProduct.setQuantity(request.getQuantity());

        // update cart
        Cart myCart = cartProduct.getCart();
        Cart updatedCart = cartService.updateCart(myCart);
        response.put("message", "Updated item from cart successfully");
        response.put("cart" , updatedCart);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeCartItem")
    public ResponseEntity<Object> deleteCartItem(@RequestParam Integer cartProductId){
        Map<String, Object> response = new HashMap<>();
        CartProduct cartProduct = cartProductService.getCartProductById(cartProductId);
        if(cartProduct == null){
            response.put("error", "CartProduct not found with this id.");
            return ResponseEntity.badRequest().body(response);
        }

        // add stock
        cartProduct.getProduct().setStock(cartProduct.getProduct().getStock() + cartProduct.getQuantity());
        productService.saveProduct(cartProduct.getProduct());

        // remove item from cart
        Cart myCart = cartProduct.getCart();
        myCart.getItems().remove(cartProduct);
        Cart updatedCart = cartService.updateCart(myCart);
        response.put("message", "Item removed from cart successfully");
        response.put("cart" , updatedCart);
        return ResponseEntity.ok(response);
    }

}