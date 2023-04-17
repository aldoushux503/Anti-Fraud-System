package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.TransactionLimit;
import com.example.antifraudsystem.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, Long> {

    boolean existsByStatusTransaction(TransactionStatus status);
}
