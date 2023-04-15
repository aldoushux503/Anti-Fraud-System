package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Card;
import com.example.antifraudsystem.service.StolenCardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Validated
@RestController
@RequestMapping("/api/antifraud")
public class StolenCardController {

    private final Logger LOGGER = LoggerFactory.getLogger(StolenCardController.class);
    private StolenCardService stolenCardService;

    @Autowired
    public StolenCardController(StolenCardService stolenCardService) {
        this.stolenCardService = stolenCardService;
    }

    @PostMapping("/stolencard")
    public ResponseEntity<?> addStolenCard(@RequestBody @Valid Card card) {
        LOGGER.info("Adding card to stolen cards {}", card.getNumber());
        return stolenCardService.addStolenCardToDataBase(card);
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<?> deleteStoleCard(@PathVariable @LuhnCheck @NotBlank String number) {
        LOGGER.info("Deleting card from stolen {}", number);
        return stolenCardService.deleteStolenCardFromDataBase(number);
    }

    @GetMapping("/stolencard")
    public ResponseEntity<?> getAllStolenCards() {
        LOGGER.info("Showing all stolen cards");
        return new ResponseEntity<>(stolenCardService.getAllCards(), HttpStatus.OK);
    }

}
