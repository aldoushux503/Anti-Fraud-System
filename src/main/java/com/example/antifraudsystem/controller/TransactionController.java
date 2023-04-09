package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.enums.TransactionStatus;
import com.example.antifraudsystem.entity.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    public TransactionController() {

    }

    @PostMapping("/transaction")
    public ResponseEntity<?> validateAmount(@RequestBody Transaction transaction) {
        long amount = transaction.amount();

        if (amount <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (amount <= 200) {
            return new ResponseEntity<>(Map.of("result", TransactionStatus.ALLOWED), HttpStatus.OK);
        } else if (amount <= 1500) {
            return new ResponseEntity<>(Map.of("result", TransactionStatus.MANUAL_PROCESSING), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("result", TransactionStatus.PROHIBITED), HttpStatus.OK);
        }
    }
}
