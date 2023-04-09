package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.dto.UserDto;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.repository.RoleRepository;
import com.example.antifraudsystem.repository.UserRepository;
import com.example.antifraudsystem.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public RegistrationController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/user")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto) {
        if (userDto.getName() == null || userDto.getUsername() == null || userDto.getPassword() == null) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.createNewUser(userDto);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
