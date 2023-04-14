package com.example.antifraudsystem.service;

import com.example.antifraudsystem.TransactionResponse;
import com.example.antifraudsystem.component.LuhnAlgorithm;
import com.example.antifraudsystem.entity.Transaction;
import com.example.antifraudsystem.enums.TransactionStatus;
import com.example.antifraudsystem.repository.CardRepository;
import com.example.antifraudsystem.repository.IpRepository;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    InetAddressValidator VALIDATOR = InetAddressValidator.getInstance();
    CardRepository cardRepository;
    IpRepository ipRepository;
    LuhnAlgorithm luhnAlgorithm;

    @Autowired
    public TransactionService(CardRepository cardRepository, IpRepository ipRepository, LuhnAlgorithm luhnAlgorithm) {
        this.cardRepository = cardRepository;
        this.ipRepository = ipRepository;
        this.luhnAlgorithm = luhnAlgorithm;
    }

    /*
    TODO
    Add logging
    */
    public ResponseEntity<?> makeTransaction(Transaction transaction) {
        long amount = transaction.getAmount();
        String cardNumber = transaction.getNumber();
        String ipAddress = transaction.getIp();

        if (!luhnAlgorithm.validateCardNumber(transaction.getNumber())
                || !VALIDATOR.isValidInet4Address(transaction.getIp())) {
            LOGGER.error(
                    "The card number or IP is not in the correct form: Card - {} IP - {}", cardNumber, ipAddress
            );
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TransactionResponse response;

        if (amount <= 200) {
            response = new TransactionResponse(TransactionStatus.ALLOWED, "none");
        } else if (amount <= 1500) {
            response = new TransactionResponse(TransactionStatus.MANUAL_PROCESSING, "amount");
        } else {
            response = new TransactionResponse(TransactionStatus.PROHIBITED, "amount");
        }

        if (cardRepository.existsByNumber(cardNumber)) {
            if (response.getResult() == TransactionStatus.PROHIBITED) {
                response.setInfo("amount, card-number");
            } else {
                response.setInfo("card-number");
            }
            response.setResult(TransactionStatus.PROHIBITED);
        }

        if (ipRepository.existsByAddress(ipAddress)) {
            if (response.getResult() == TransactionStatus.PROHIBITED) {
                response.setInfo(response.getInfo() + ", " + "ip");
            } else {
                if (response.getInfo().contains("card-number")) {
                    response.setInfo("card-number, ip");
                } else {
                    response.setInfo("ip");
                }
            }
            response.setResult(TransactionStatus.PROHIBITED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
