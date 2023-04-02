package com.example.antifraudsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class ValidationController {

    @PostMapping("/api/antifraud/transaction")
    public void validateAmount(@RequestBody String requestJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

    }
}
