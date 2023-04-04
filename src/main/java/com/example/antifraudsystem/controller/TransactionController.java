package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.dto.UserDto;
import com.example.antifraudsystem.entity.Transaction;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    UserRepository userRepository;

    @Autowired
    public TransactionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/antifraud/transaction")
    public ResponseEntity<?> validateAmount(@RequestBody Transaction transaction) {
        long amount = transaction.amount();

        if (amount <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (amount <= 200) {
            return new ResponseEntity<>(Map.of("result", "ALLOWED"), HttpStatus.OK);
        } else if (amount <= 1500) {
            return new ResponseEntity<>(Map.of("result", "MANUAL_PROCESSING"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("result", "PROHIBITED"), HttpStatus.OK);
        }
    }

    @GetMapping("/api/auth/list")
    public List<UserDto> showAllUsers() {
        List<UserDto> res = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            res.add(new UserDto(user.getName(), user.getUsername(), null));
        }

        return res;
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                userRepository.delete(user);
                Map<String, String> name = Map.of("username", user.getUsername());
                Map<String, String> status = Map.of("status", "Deleted successfully!");
                return new ResponseEntity<>(List.of(name, status), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
