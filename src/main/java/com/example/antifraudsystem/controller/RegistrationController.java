package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.user.User;
import com.example.antifraudsystem.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final AuthService authService;
    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    public RegistrationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        LOGGER.info("Registering user: {}", user.getName());
        return authService.createNewUser(user);
    }
}
