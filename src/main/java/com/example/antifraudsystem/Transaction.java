package com.example.antifraudsystem;

public class Transaction {
    private final int amount;

    public Transaction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
