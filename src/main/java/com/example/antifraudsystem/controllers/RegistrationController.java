package com.example.antifraudsystem.controllers;

import com.example.antifraudsystem.User;
import com.example.antifraudsystem.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    UserRepository userRepo;

    PasswordEncoder encoder;
    @Autowired
    public RegistrationController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("/api/auth/user")
    public void register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }
}
