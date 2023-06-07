package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.dto.UserLockDto;
import com.example.antifraudsystem.dto.UserRoleDto;
import com.example.antifraudsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;


    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> showAllUsers() {
        log.info("Showing all users");
        return new ResponseEntity<>(authService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") @Valid String username) {
        log.info("Deleting user with username: {}", username);
        return authService.deleteUser(username);
    }


    @PutMapping("/role")
    public ResponseEntity<?> changeUserRole(@RequestBody UserRoleDto userRoleDto) {
        log.info("Processing change role for username: {}, newRole: {}", userRoleDto.username(), userRoleDto.role());
        return authService.changeUserRole(userRoleDto);
    }

    @PutMapping("/access")
    public ResponseEntity<?> changeLockStatus(@RequestBody UserLockDto userLockDto) {
        log.info("Processing change Lock for username: {}, newLock: {}", userLockDto.username(), userLockDto.operation());
        return authService.changeLockStatus(userLockDto);
    }
}
