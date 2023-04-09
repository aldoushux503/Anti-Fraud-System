package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.dto.UserDto;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.service.AuthService;
import com.example.antifraudsystem.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final AuthService authService;

    @Autowired
    public RegistrationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto) {
        if (userDto.getName() == null || userDto.getUsername() == null || userDto.getPassword() == null) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = authService.createNewUser(userDto);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
