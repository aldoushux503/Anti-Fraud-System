package com.example.antifraudsystem;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @PostMapping("/api/auth/user")
    public void registrationUser() {
        System.out.println("registrate");
    }
}
