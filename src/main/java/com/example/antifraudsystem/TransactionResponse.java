package com.example.antifraudsystem;

import com.example.antifraudsystem.entity.Transaction;
import com.example.antifraudsystem.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private TransactionStatus result;

    private String info;

}
