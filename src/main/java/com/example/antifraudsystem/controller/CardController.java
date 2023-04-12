package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Card;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/antifraud")
public class CardController {

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
