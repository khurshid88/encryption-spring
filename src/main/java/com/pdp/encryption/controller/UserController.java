package com.pdp.encryption.controller;

import com.pdp.encryption.model.User;
import com.pdp.encryption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.pdp.encryption.utils.Symmetric.decrypt;
import static com.pdp.encryption.utils.Symmetric.encrypt;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("api/users")
    public HttpEntity<?> getUsers() {
        List<User> userList = userService.getUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("api/users/{id}")
    public HttpEntity<?> getUserById(@PathVariable(value = "id") int userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/users")
    public HttpEntity<?> createUser(@RequestBody User user) {
        System.out.println(user.toString());
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
