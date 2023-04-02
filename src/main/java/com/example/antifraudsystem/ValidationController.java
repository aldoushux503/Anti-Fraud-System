package com.example.antifraudsystem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ValidationController {

    @PostMapping("/api/antifraud/transaction")
    public void validateAmount(@RequestBody String requestJson) {
        System.out.println(requestJson);
    }
}
