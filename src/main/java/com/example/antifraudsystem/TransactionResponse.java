package com.example.antifraudsystem;

import com.example.antifraudsystem.entity.Transaction;
import com.example.antifraudsystem.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TransactionResponse {

    private TransactionStatus result;

    private String info;

    public TransactionResponse(TransactionStatus result, String info) {
        this.result = result;
        this.info = info;
    }
}
