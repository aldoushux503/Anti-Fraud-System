package com.example.antifraudsystem.dto;

import com.example.antifraudsystem.enums.ActivityOperation;

public record UserLockDto(String username, ActivityOperation operation) {
}
