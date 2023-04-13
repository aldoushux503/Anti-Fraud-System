package com.example.antifraudsystem;

import java.util.ArrayList;
import java.util.List;

public record TransactionResponse(String result, List<String> info) {
}
