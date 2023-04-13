package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Card;
import com.example.antifraudsystem.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/antifraud")
public class CardController {

    private final Logger LOGGER = LoggerFactory.getLogger(CardController.class);
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/stolencard")
    public void addStolenCard(@RequestBody Card card) {

    }

    @DeleteMapping("/stolencard/{number}")
    public void deleteStoleCard(@PathVariable String number) {

    }

    @GetMapping("/stolencard")
    public void getAllStolenCards() {

    }

}
