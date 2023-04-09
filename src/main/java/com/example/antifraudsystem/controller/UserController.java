package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.dto.UserLockDto;
import com.example.antifraudsystem.dto.UserRoleDto;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> showAllUsers() {
        LOGGER.info("Show all users");
        return new ResponseEntity<>(authService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") @Valid String username) {
        LOGGER.info("Deleting user with username: {}", username);
        return authService.deleteUser(username);
    }


    @PutMapping("/role")
    public ResponseEntity<?> changeUserRole(@RequestBody UserRoleDto userRoleDto) {
        LOGGER.info("Processing change role for username: {}, newRole: {}", userRoleDto.username(), userRoleDto.role());
        return authService.changeUserRole(userRoleDto);
    }

    @PutMapping("/access")
    public ResponseEntity<?> changeLockStatus(@RequestBody UserLockDto userLockDto) {
        LOGGER.info("Processing change Lock for username: {}, lock: {}", userLockDto.username(), userLockDto.operation());
        return authService.changeLockStatus(userLockDto);
    }
}
