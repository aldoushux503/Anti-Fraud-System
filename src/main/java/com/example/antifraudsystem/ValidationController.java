package com.example.antifraudsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ValidationController {

    @PostMapping("/api/antifraud/transaction")
    public Map<String, String> validateAmount(@RequestBody String requestJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Transaction transaction = mapper.readValue(requestJson, Transaction.class);

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
