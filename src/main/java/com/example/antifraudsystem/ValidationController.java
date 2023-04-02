package com.example.antifraudsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ValidationController {

    @PostMapping("/api/antifraud/transaction")
    public Map<String, String> validateAmount(@RequestBody String requestJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Transaction transaction = mapper.readValue(requestJson, Transaction.class);

        Map<String, String> res = new HashMap<>();
        if (transaction.amount() <= 200) {
            res.put("amount", "ALLOWED");
        } else if (transaction.amount() <= 1500) {
            res.put("amount", "MANUAL_PROCESSING");
        } else {
            res.put("amount", "PROHIBITED");
        }

        return res;
    }
}
