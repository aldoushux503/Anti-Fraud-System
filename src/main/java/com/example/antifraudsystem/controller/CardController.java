package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Card;
import com.example.antifraudsystem.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addStolenCard(@RequestBody Card card) {
        LOGGER.info("Adding card to stolen cards {}", card.getNumber());
        return cardService.addStolenCardToDataBase(card);
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<?> deleteStoleCard(@PathVariable String number) {
        LOGGER.info("Deleting card from stolen {}", number);
        return cardService.deleteStolenCardFromDataBase(number);
    }

    @GetMapping("/stolencard")
    public ResponseEntity<?> getAllStolenCards() {
        LOGGER.info("Showing all stolen cards");
        return new ResponseEntity<>(cardService.getAllCards(), HttpStatus.OK);
    }

}
