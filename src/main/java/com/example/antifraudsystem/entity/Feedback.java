package com.example.antifraudsystem.entity;

import com.example.antifraudsystem.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Feedback {

    @NotNull(message = "Transaction id is null")
    private long transactionId;

    @NotNull(message = "Feedback status is null")
    private TransactionStatus feedback;
}
