package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.Ipv4;
import com.example.antifraudsystem.entity.Ip;
import com.example.antifraudsystem.service.SuspiciousIpService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/antifraud")
public class SuspiciousIpController {

    private final Logger LOGGER = LoggerFactory.getLogger(SuspiciousIpController.class);
    SuspiciousIpService suspiciousIpService;


    @Autowired
    public SuspiciousIpController(SuspiciousIpService suspiciousIpService) {
        this.suspiciousIpService = suspiciousIpService;
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<?> addSuspiciousIp(@RequestBody @Valid Ip ip) {
        LOGGER.info("Adding IP address to suspicious {}", ip.getAddress());
        return suspiciousIpService.addSuspiciousIpToDataBase(ip);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<?> deleteSuspiciousIp(@PathVariable @Ipv4 @NotBlank String ip) {
        LOGGER.info("Deleting IP address from suspicious {}", ip);
        return suspiciousIpService.deleteSuspiciousIpFromDataBase(ip);
    }

    @GetMapping("/suspicious-ip")
    public ResponseEntity<?> showAllSuspiciousIp() {
        LOGGER.info("Showing all suspicious Ip");
        return new ResponseEntity<>(suspiciousIpService.getAllSuspiciousIp(), HttpStatus.OK);
    }
}
