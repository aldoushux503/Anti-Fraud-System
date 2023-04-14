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
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private final InetAddressValidator VALIDATOR = InetAddressValidator.getInstance();
    private final LuhnAlgorithm luhnAlgorithm;
    private CardRepository cardRepository;
    private IpRepository ipRepository;

    @Autowired
    public TransactionService(CardRepository cardRepository, IpRepository ipRepository, LuhnAlgorithm luhnAlgorithm) {
        this.cardRepository = cardRepository;
        this.ipRepository = ipRepository;
        this.luhnAlgorithm = luhnAlgorithm;
    }

    public ResponseEntity<?> makeTransaction(Transaction transaction) {
        if (!luhnAlgorithm.validateCardNumber(transaction.getNumber())
                || !VALIDATOR.isValidInet4Address(transaction.getIp())) {
            LOGGER.error(
                    "The card number or IP is not in the correct form: Card - {} IP - {}",
                    transaction.getNumber(), transaction.getIp()
            );
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        long amount = transaction.getAmount();
        String cardNumber = transaction.getNumber();
        String ipAddress = transaction.getIp();
        TransactionResponse response = new TransactionResponse();
        List<String> violations = new ArrayList<>();

        response.setResult(checkAmount(amount));

        if (response.getResult() == TransactionStatus.PROHIBITED) {
            LOGGER.info("Adding amount violations");
            violations.add("amount");
        }
        if (cardRepository.existsByNumber(cardNumber)) {
            LOGGER.info("Adding credit card violations");
            violations.add("card-number");
            response.setResult(TransactionStatus.PROHIBITED);
        }
        if (ipRepository.existsByAddress(ipAddress)) {
            LOGGER.info("Adding IP violations");
            violations.add("ip");
            response.setResult(TransactionStatus.PROHIBITED);
        }

        String info = violations.isEmpty()
                ? response.getResult() == TransactionStatus.MANUAL_PROCESSING ? "amount" : "none"
                : violations.stream().sorted().collect(Collectors.joining(", "));

        response.setInfo(info);

        LOGGER.info("Returning transaction response {} {}", response.getResult(), response.getInfo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private TransactionStatus checkAmount(long amount) {
        LOGGER.info("Checking amount {}", amount);
        if (amount <= 200) {
            return TransactionStatus.ALLOWED;
        } else if (amount <= 1500) {
            return TransactionStatus.MANUAL_PROCESSING;
        } else {
            return TransactionStatus.PROHIBITED;
        }
    }
}
