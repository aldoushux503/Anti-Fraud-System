package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Feedback;
import com.example.antifraudsystem.entity.transaction.Transaction;
import com.example.antifraudsystem.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.LuhnCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> makeTransaction(@RequestBody @Valid Transaction transaction) {
        log.info("Saving transaction to database without result");
        transactionService.addTransactionToDataBase(transaction);

        log.info("Processing transaction result and info {}, {}", transaction.getNumber(), transaction.getIp());
        return transactionService.processTransaction(transaction);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showAllTransactions() {
        log.info("Showing all transactions");
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<?> showSpecifiedTransactions(@PathVariable @LuhnCheck String number) {
        log.info("Showing transactions by a card number");
        List<Transaction> transactions = transactionService.getTransactionsByNumber(number);

        if (transactions.isEmpty()) {
            log.info("Transaction not found in database");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PutMapping("/transaction")
    public ResponseEntity<?> makeFeedback(@RequestBody @Valid Feedback feedback) {
        return transactionService.addFeedbackToTransaction(feedback);
    }

}
