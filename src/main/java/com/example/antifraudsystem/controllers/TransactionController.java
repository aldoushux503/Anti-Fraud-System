package com.example.antifraudsystem.controllers;

import com.example.antifraudsystem.Transaction;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TransactionController {

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
    public void showAllUsers() {
        System.out.println("list");
    }

    @DeleteMapping("/api/auth/user/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        System.out.println("delete");
    }

}
