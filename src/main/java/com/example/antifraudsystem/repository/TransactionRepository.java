package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByNumber(String number);
}
