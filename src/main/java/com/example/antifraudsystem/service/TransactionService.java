package com.example.antifraudsystem.service;

import com.example.antifraudsystem.TransactionResponse;
import com.example.antifraudsystem.entity.Transaction;
import com.example.antifraudsystem.enums.TransactionStatus;
import com.example.antifraudsystem.repository.CardRepository;
import com.example.antifraudsystem.repository.IpRepository;
import com.example.antifraudsystem.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private CardRepository cardRepository;
    private IpRepository ipRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(CardRepository cardRepository,
                              IpRepository ipRepository,
                              TransactionRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.ipRepository = ipRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<?> addTransactionToDataBase(Transaction transaction) {
        LOGGER.info("Saving transaction to database");
        transactionRepository.save(transaction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> makeTransaction(Transaction transaction) {
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


        List<Transaction> transactions = transactionRepository.findAllByNumber(cardNumber);
        LocalDateTime now = LocalDateTime.now();
        String closestIpAddress = null;
        Duration closestDuration = null;

        for (Transaction t : transactions) {
            Duration duration = Duration.between(t.getDate(),  now);
            if (closestDuration == null || duration.compareTo(closestDuration) < 0) {
                closestDuration = duration;
                closestIpAddress = t.getIp();
            }
        }
        System.out.println(closestIpAddress);
        int counter = 0;

        for (Transaction t : transactions) {
            if (!Objects.equals(t.getIp(), closestIpAddress)) {
                counter++;
            }
        }

        if (counter == 3) {
            violations.add("ip-correlation");
            response.setResult(TransactionStatus.MANUAL_PROCESSING);
        }
        if (counter > 3) {
            violations.add("ip-correlation");
            response.setResult(TransactionStatus.PROHIBITED);
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

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
