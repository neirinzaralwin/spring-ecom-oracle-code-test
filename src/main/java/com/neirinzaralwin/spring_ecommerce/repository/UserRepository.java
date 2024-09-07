package com.neirinzaralwin.spring_ecommerce.repository;

import com.neirinzaralwin.spring_ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByName(String name);

}