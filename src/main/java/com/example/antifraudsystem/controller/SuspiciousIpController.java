package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.util.Ipv4;
import com.example.antifraudsystem.entity.Ip;
import com.example.antifraudsystem.service.SuspiciousIpService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/antifraud")
public class SuspiciousIpController {

    SuspiciousIpService suspiciousIpService;


    @Autowired
    public SuspiciousIpController(SuspiciousIpService suspiciousIpService) {
        this.suspiciousIpService = suspiciousIpService;
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<?> addSuspiciousIp(@RequestBody @Valid Ip ip) {
        log.info("Adding IP address to suspicious {}", ip.getAddress());
        return suspiciousIpService.addSuspiciousIpToDataBase(ip);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<?> deleteSuspiciousIp(@PathVariable @Ipv4 @NotBlank String ip) {
        log.info("Deleting IP address from suspicious {}", ip);
        return suspiciousIpService.deleteSuspiciousIpFromDataBase(ip);
    }

    @GetMapping("/suspicious-ip")
    public ResponseEntity<?> showAllSuspiciousIp() {
        log.info("Showing all suspicious Ip");
        return new ResponseEntity<>(suspiciousIpService.getAllSuspiciousIp(), HttpStatus.OK);
    }
}
