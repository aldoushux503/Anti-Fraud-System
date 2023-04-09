package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.dto.UserRoleDto;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/api/auth/list")
    public List<User> showAllUsers() {
        return userService.showAllUsers();
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        User deletedUser = userService.deleteUser(username);

        if (deletedUser != null) {
            Map<String, String> res = Map.of(
                    "username", deletedUser.getUsername(),
                    "status", "Deleted successfully!"
            );
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/api/auth/role")
    public ResponseEntity<?> changeUserRole(@RequestBody UserRoleDto userRoleDto) {
        return userService.changeUserRole(userRoleDto);
    }

//    @PutMapping("/api/auth/access")
//    public ResponseEntity<?> changeLockStatus(@RequestBody UserLockDto userRoleDto) {
//        return userService.changeUserRole(userRoleDto);
//    }
}
