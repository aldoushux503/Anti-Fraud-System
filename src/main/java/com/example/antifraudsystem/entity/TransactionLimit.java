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
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column
    private long limit;

    public TransactionLimit(TransactionStatus status, long limit) {
        this.status = status;
        this.limit = limit;
    }
}

