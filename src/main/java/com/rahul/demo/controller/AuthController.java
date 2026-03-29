package com.rahul.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.demo.model.User;
import com.rahul.demo.repository.UserRepository;
import com.rahul.demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        Optional<User> userOptional = userRepository
                .findByUsername(username);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return jwtUtil.generateToken(username);
        } else {
            return "Invalid credentials";
        }
    }
}
