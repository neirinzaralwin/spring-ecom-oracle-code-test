package com.neirinzaralwin.spring_ecommerce.service;

import com.neirinzaralwin.spring_ecommerce.entity.User;
import java.util.List;

import com.neirinzaralwin.spring_ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        return repository.saveAll(users);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User with this email not found"));
    }

    public String deleteUser(int id) {
        repository.deleteById(id);
        return "User removed !! " + id;
    }

    public User updateUser(User user) {
        User existingUser = repository.findById(user.getId())
                .orElseThrow(() -> new BadCredentialsException("No user found with this id"));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return repository.save(existingUser);
    }
}