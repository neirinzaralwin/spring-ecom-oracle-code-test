package com.neirinzaralwin.spring_ecommerce.repository;

import com.neirinzaralwin.spring_ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository  extends JpaRepository<Order,Integer> {

    Optional<Order> findByUser_UserId(int userId);


}
