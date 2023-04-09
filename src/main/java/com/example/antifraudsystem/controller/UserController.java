package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.dto.UserLockDto;
import com.example.antifraudsystem.dto.UserRoleDto;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;

    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/list")
    public List<User> showAllUsers() {
        return authService.getAllUsers();
    }

    @DeleteMapping("user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        User deletedUser = authService.deleteUser(username);

        if (deletedUser != null) {
            Map<String, String> res = Map.of(
                    "username", deletedUser.getUsername(),
                    "status", "Deleted successfully!"
            );
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/role")
    public ResponseEntity<?> changeUserRole(@RequestBody UserRoleDto userRoleDto) {
        return authService.changeUserRole(userRoleDto);
    }

    @PutMapping("/access")
    public ResponseEntity<?> changeLockStatus(@RequestBody UserLockDto userLockDto) {
        return authService.changeLockStatus(userLockDto);
    }
}
