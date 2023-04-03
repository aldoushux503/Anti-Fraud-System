package com.example.antifraudsystem.controllers;

import com.example.antifraudsystem.User;
import com.example.antifraudsystem.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> register(@RequestBody User newUser) {
        Iterable<User> users = userRepo.findAll();

        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepo.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
