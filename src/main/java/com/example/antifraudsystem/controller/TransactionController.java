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

    private final UserRepository userRepository;

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
}
