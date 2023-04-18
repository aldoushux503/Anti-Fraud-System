package com.example.antifraudsystem.response;

import com.example.antifraudsystem.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private TransactionStatus result;

    private String info;

}
