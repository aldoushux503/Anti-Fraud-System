package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.Card;
import com.example.antifraudsystem.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class StolenCardService {

    private CardRepository cardRepository;
    @Autowired
    public StolenCardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> addStolenCardToDataBase(Card card) {
        if (cardRepository.existsByNumber(card.getNumber())) {
            log.error("Card already exist in database {}", card.getNumber());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        log.info("Saving card to database {}", card);
        cardRepository.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteStolenCardFromDataBase(String number) {
        Optional<Card> card = cardRepository.findByNumber(number);

        if (card.isEmpty()) {
            log.error("Card not found in database {}", number);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Deleting card from database {}", number);
        cardRepository.delete(card.get());

        String res = String.format("Card %s successfully removed!", card.get().getNumber());
        return new ResponseEntity<>(Map.of("status", res), HttpStatus.OK);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
