package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Ip;
import com.example.antifraudsystem.service.IpService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/antifraud")
public class IpController {

    IpService ipService;

    @Autowired
    public IpController(IpService ipService) {
        this.ipService = ipService;
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<?> addSuspiciousIp(@RequestBody Ip ip) {
        return ipService.addSuspiciousIpToDataBase(ip);
    }
}
