package com.neirinzaralwin.spring_ecommerce.controller;

import com.neirinzaralwin.spring_ecommerce.entity.User;
import com.neirinzaralwin.spring_ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/createUser")
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/addUsers")
    public List<User> addUsers(@RequestBody List<User> users) {
        return service.saveUsers(users);
    }

    @GetMapping("/Users")
    public List<User> findAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/UserById/{id}")
    public User findUserByEmpId(@PathVariable int id) {
        return service.getUserById(id);
    }

    @GetMapping("/User/{name}")
    public User findUserByFirstName(@PathVariable String name) {
        return service.getUserByName(name);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        return service.deleteUser(id);
    }
}