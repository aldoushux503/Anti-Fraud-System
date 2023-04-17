package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.entity.TransactionLimit;
import com.example.antifraudsystem.enums.TransactionStatus;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.repository.TransactionLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionLimitService {

    TransactionLimitRepository limitRepository;

    @Autowired
    public TransactionLimitService(TransactionLimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Bean
    private void addAllTransactionStatusesAndLimit() {
        if (!limitRepository.existsByStatus(TransactionStatus.ALLOWED)
                || !limitRepository.existsByStatus(TransactionStatus.MANUAL_PROCESSING)
                || !limitRepository.existsByStatus(TransactionStatus.PROHIBITED)) {

            TransactionLimit allowedLimit = new TransactionLimit(TransactionStatus.ALLOWED, 200);
            TransactionLimit manualLimit = new TransactionLimit(TransactionStatus.ALLOWED, 1500);
            TransactionLimit prohibitedLimit = new TransactionLimit(TransactionStatus.ALLOWED, Long.MAX_VALUE);


            limitRepository.saveAll(List.of(allowedLimit, manualLimit, prohibitedLimit));
        }
    }
}
