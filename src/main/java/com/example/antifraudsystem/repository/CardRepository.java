package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNumber(String number);

    Optional<Card> findByNumber(String number);
}
