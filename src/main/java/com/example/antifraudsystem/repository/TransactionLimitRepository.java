package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.TransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, Long> {
}
