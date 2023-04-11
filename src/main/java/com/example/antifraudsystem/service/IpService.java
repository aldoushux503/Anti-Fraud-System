package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.Ip;
import com.example.antifraudsystem.repository.IpRepository;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IpService {

    InetAddressValidator VALIDATOR = InetAddressValidator.getInstance();
    IpRepository ipRepository;

    @Autowired
    public IpService(IpRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public ResponseEntity<?> addSuspiciousIpToDataBase(Ip ip)  {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
