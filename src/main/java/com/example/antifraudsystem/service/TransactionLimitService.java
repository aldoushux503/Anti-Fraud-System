package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.transaction.TransactionLimit;
import com.example.antifraudsystem.enums.TransactionStatus;
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
        if (!limitRepository.existsByStatusTransaction(TransactionStatus.ALLOWED)
                || !limitRepository.existsByStatusTransaction(TransactionStatus.MANUAL_PROCESSING)
                || !limitRepository.existsByStatusTransaction(TransactionStatus.PROHIBITED)) {

            TransactionLimit allowedLimit = new TransactionLimit(TransactionStatus.ALLOWED, 200);
            TransactionLimit manualLimit = new TransactionLimit(TransactionStatus.ALLOWED, 1500);
            TransactionLimit prohibitedLimit = new TransactionLimit(TransactionStatus.ALLOWED, Long.MAX_VALUE);


            limitRepository.saveAll(List.of(allowedLimit, manualLimit, prohibitedLimit));
        }
    }

    public void processNewLimit(TransactionStatus oldStatus, TransactionStatus newStatus, long transactionValue) {
        List<TransactionLimit> transactionLimits = limitRepository.findAll();

        if (oldStatus.ordinal() < newStatus.ordinal()) {
            for(int i = oldStatus.ordinal(); i < newStatus.ordinal(); i++) {
                TransactionLimit limit = transactionLimits.get(i);
                long new_limit = (long) Math.ceil(0.8 * limit.getLimitValue() - 0.2 * transactionValue);
                limit.setLimitValue(new_limit);
                limitRepository.save(limit);
            }
        } else {
            for(int i = newStatus.ordinal(); i < oldStatus.ordinal(); i++) {
                TransactionLimit limit = transactionLimits.get(i);
                long new_limit = (long) Math.ceil(0.8 * limit.getLimitValue() + 0.2 * transactionValue);
                limit.setLimitValue(new_limit);
                limitRepository.save(limit);
            }
        }
    }
}
