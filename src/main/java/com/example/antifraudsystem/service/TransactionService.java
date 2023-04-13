package com.example.antifraudsystem.service;

import com.example.antifraudsystem.component.LuhnAlgorithm;
import com.example.antifraudsystem.repository.CardRepository;
import com.example.antifraudsystem.repository.IpRepository;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    InetAddressValidator VALIDATOR = InetAddressValidator.getInstance();
    CardRepository cardRepository;
    IpRepository ipRepository;
    LuhnAlgorithm luhnAlgorithm;

    @Autowired
    public TransactionService(CardRepository cardRepository, IpRepository ipRepository, LuhnAlgorithm luhnAlgorithm) {
        this.cardRepository = cardRepository;
        this.ipRepository = ipRepository;
        this.luhnAlgorithm = luhnAlgorithm;
    }



}
