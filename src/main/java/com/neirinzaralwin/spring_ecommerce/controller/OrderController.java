package com.neirinzaralwin.spring_ecommerce.controller;

import com.neirinzaralwin.spring_ecommerce.adapter.CreateOrderAdapter;
import com.neirinzaralwin.spring_ecommerce.constant.ORDER_STATUS;
import com.neirinzaralwin.spring_ecommerce.entity.Cart;
import com.neirinzaralwin.spring_ecommerce.entity.Order;
import com.neirinzaralwin.spring_ecommerce.entity.User;
import com.neirinzaralwin.spring_ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/orders")
@RestController
public class OrderController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<Object> findAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        Map<String, Object> response = new HashMap<>();
        response.put("data", orders);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-status")
    public ResponseEntity<Object> changeOrderStatus(@RequestParam Integer orderId, @RequestParam String status) {
        HashMap<String, Object> response = new HashMap<>();
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            response.put("message", "Order not found");
            return ResponseEntity.badRequest().body(response);
        }

        // cast String to ORDER_STATUS
        try{
            order.setStatus(ORDER_STATUS.valueOf(status));
        }catch (IllegalArgumentException e){
            response.put("message", "Invalid status");
            return ResponseEntity.badRequest().body(response);
        }
        orderService.updateOrder(order);

        response.put("message", "Order status updated successfully");
        response.put("data", order);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestParam Integer userId) {
        HashMap<String, Object> response = new HashMap<>();

        User user = userService.getUserById(userId);
        if (user == null) {
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }

        Cart cart = cartService.getCartByUserId(userId).orElse(null);
        if (cart == null) {
            response.put("message", "Cart not found");
            return ResponseEntity.badRequest().body(response);
        }

        if(cart.getItems().isEmpty()){
            response.put("message", "Cart is empty, can't create order");
            return ResponseEntity.badRequest().body(response);
        }


        // user adapter to create order
        Order newOrder = CreateOrderAdapter.transform(cart);
        orderService.createOrder(newOrder);
        // clear cart
        cart.getItems().clear();
        cartService.updateCart(cart);

        response.put("message", "Order created successfully");
        response.put("data", newOrder);
        return ResponseEntity.ok().body(response);
    }
}