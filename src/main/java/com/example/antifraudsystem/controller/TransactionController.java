package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Feedback;
import com.example.antifraudsystem.entity.Transaction;
import com.example.antifraudsystem.service.TransactionService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.LuhnCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public ResponseEntity<?> makeTransaction(@RequestBody @Valid Transaction transaction) {
        LOGGER.info("Saving transaction to database without result");
        transactionService.addTransactionToDataBase(transaction);

        LOGGER.info("Processing transaction result and info {}, {}", transaction.getNumber(), transaction.getIp());
        return transactionService.processTransaction(transaction);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showAllTransactions() {
        LOGGER.info("Showing all transactions");
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<?> showSpecifiedTransactions(@PathVariable @LuhnCheck String number) {
        LOGGER.info("Showing transactions by a card number");
        List<Transaction> transactions = transactionService.getTransactionsByNumber(number);

        if (transactions.isEmpty()) {
            LOGGER.info("Transaction not found in database");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PutMapping("/transaction")
    public ResponseEntity<?> makeFeedback(@RequestBody @Valid Feedback feedback) {
        return transactionService.addFeedbackToTransaction(feedback);
    }

}
