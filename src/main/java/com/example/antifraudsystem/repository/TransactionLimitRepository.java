package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.transaction.TransactionLimit;
import com.example.antifraudsystem.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, Long> {

    boolean existsByStatusTransaction(TransactionStatus status);
}
