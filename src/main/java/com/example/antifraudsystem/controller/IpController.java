package com.example.antifraudsystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/antifraud")
public class IpController {

    @PostMapping("/suspicious-ip")
    public void addSuspiciousIp() {

    }
}
