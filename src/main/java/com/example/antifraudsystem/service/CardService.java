package com.example.antifraudsystem.service;

import com.example.antifraudsystem.component.LuhnAlgorithm;
import com.example.antifraudsystem.entity.Card;
import com.example.antifraudsystem.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final Logger LOGGER = LoggerFactory.getLogger(CardService.class);
    private final LuhnAlgorithm luhnAlgorithm;
    private CardRepository cardRepository;

    @Autowired
    public CardService(LuhnAlgorithm luhnAlgorithm, CardRepository cardRepository) {
        this.luhnAlgorithm = luhnAlgorithm;
        this.cardRepository = cardRepository;
    }


    public ResponseEntity<?> addStolenCardToDataBase(Card card) {
        if (!luhnAlgorithm.validateCardNumber(card.getNumber())) {
            LOGGER.error("Card has the wrong number. {}", card.getNumber());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (cardRepository.existsByNumber(card.getNumber())) {
            LOGGER.error("Card already exist in database. {}", card.getNumber());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        LOGGER.error("Saving card to database. {}", card);
        cardRepository.save(card);
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteStolenCardFromDataBase(String number) {
        return null;
    }
}
