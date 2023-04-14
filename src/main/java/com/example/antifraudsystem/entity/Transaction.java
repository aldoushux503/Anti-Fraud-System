package com.example.antifraudsystem.entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Min(1)
    private long amount;

    @NotBlank
    private String ip;

    @NotBlank
    private String number;
}
