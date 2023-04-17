package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction_limits")
public class TransactionLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus statusTransaction;

    @Column
    private long limitValue;

    public TransactionLimit(TransactionStatus statusTransaction, long limitValue) {
        this.statusTransaction = statusTransaction;
        this.limitValue = limitValue;
    }
}

