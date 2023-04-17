package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.enums.TransactionStatus;
import jakarta.persistence.*;

@Entity(name = "transaction_limits")
public class TransactionLimit {

    @Id
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column
    private int limit;



}

