package com.neirinzaralwin.spring_ecommerce.service;

import com.neirinzaralwin.spring_ecommerce.entity.Order;
import com.neirinzaralwin.spring_ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrdersByUserId(int userId) {
        return orderRepository.findByUser_UserId(userId).orElseThrow();
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public String deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
        return "Order deleted successfully";
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

}