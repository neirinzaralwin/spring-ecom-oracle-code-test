package com.neirinzaralwin.spring_ecommerce.controller;

import com.neirinzaralwin.spring_ecommerce.entity.User;
import com.neirinzaralwin.spring_ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/User")
    public ResponseEntity<Object> findUserByEmpId(@RequestParam(required = false) Integer id,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String email) {
        if (id != null) {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } else if (name != null) {
            User user = userService.getUserByName(name);
            return ResponseEntity.ok(user);
        } else if (email != null) {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "please provide id, name, or email");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }



//    @GetMapping("/User/{name}")
//    public User findUserByFirstName(@PathVariable String name) {
//        return userService.getUserByName(name);
//    }
//
//    @GetMapping("/User/{email}")
//    public User findUserByEmail(@PathVariable String email) {
//        return userService.getUserByEmail(email);
//    }

//    @PostMapping("/createUser")
//    public User addUser(@RequestBody User user) {
//        return userService.saveUser(user);
//    }
//
//    @PostMapping("/addUsers")
//    public List<User> addUsers(@RequestBody List<User> users) {
//        return userService.saveUsers(users);
//    }
//
//    @PutMapping("/update")
//    public User updateUser(@RequestBody User user) {
//        return userService.updateUser(user);
//    }

//    @DeleteMapping("/delete/{id}")
//    public String deleteUser(@PathVariable int id) {
//        return userService.deleteUser(id);
//    }
}