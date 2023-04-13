package com.example.antifraudsystem.service;

import com.example.antifraudsystem.component.LuhnAlgorithm;
import com.example.antifraudsystem.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
