package com.neirinzaralwin.spring_ecommerce.adapter;

import com.neirinzaralwin.spring_ecommerce.constant.ORDER_STATUS;
import com.neirinzaralwin.spring_ecommerce.entity.Cart;
import com.neirinzaralwin.spring_ecommerce.entity.Order;
import com.neirinzaralwin.spring_ecommerce.entity.OrderProduct;
import com.neirinzaralwin.spring_ecommerce.entity.CartProduct;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CreateOrderAdapter {

    public static Order transform(Cart cart) {
        // Create new order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(ORDER_STATUS.PENDING);



        List<OrderProduct> orderProducts = cart.getItems().stream().map(cartProduct -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQuantity(cartProduct.getQuantity());
            orderProduct.setPrice(cartProduct.getPrice());
            orderProduct.setOrder(order);
            return orderProduct;
        }).collect(Collectors.toList());

        order.setItems(orderProducts);
        return order;
    }
}