package com.example.jdstreetwear.controller;

import com.example.jdstreetwear.model.User;
import com.example.jdstreetwear.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String MESSAGE = "message";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put(MESSAGE, "Login successful");
            response.put("role", user.get().getRole());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap(MESSAGE, "Invalid Credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put(MESSAGE, "Email already in use");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        user.setRole("customer");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, "User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
