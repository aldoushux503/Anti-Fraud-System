package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.enums.TransactionStatus;
import com.example.antifraudsystem.entity.Transaction;
import com.example.antifraudsystem.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {
    TransactionService transactionService;
    Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> validateAmount(@RequestBody @Valid Transaction transaction) {
        LOGGER.info(
                "Transaction processing {}, {}, {}",
                transaction.getAmount(), transaction.getNumber(), transaction.getIp()
        );
        return transactionService.makeTransaction(transaction);
    }
}
