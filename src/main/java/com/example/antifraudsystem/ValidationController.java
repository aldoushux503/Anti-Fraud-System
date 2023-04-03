package com.example.antifraudsystem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class ValidationController {

    @PostMapping("/api/antifraud/transaction")
    public Map<String, String> validateAmount(@RequestBody TransactionService transaction) {
        if (transaction.amount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (transaction.amount() <= 200) {
            return Map.of("result", "ALLOWED");
        } else if (transaction.amount() <= 1500) {
            return Map.of("result", "MANUAL_PROCESSING");
        } else {
            return Map.of("result", "PROHIBITED");
        }
    }
}
