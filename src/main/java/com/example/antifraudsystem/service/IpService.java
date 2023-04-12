package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.Ip;
import com.example.antifraudsystem.repository.IpRepository;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IpService {

    InetAddressValidator VALIDATOR = InetAddressValidator.getInstance();
    private final Logger LOGGER = LoggerFactory.getLogger(IpService.class);
    IpRepository ipRepository;

    @Autowired
    public IpService(IpRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public ResponseEntity<?> addSuspiciousIpToDataBase(Ip ip)  {
        String ipAddress = ip.getAddress();

        if (!VALIDATOR.isValidInet4Address(ipAddress)) {
            LOGGER.error("Invalid Ip address format {}", ipAddress);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ipRepository.existsByAddress(ipAddress)) {
            LOGGER.error("Ip already exists {}", ipAddress);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        LOGGER.error("Saving Ip to database {}", ipAddress);
        ipRepository.save(ip);
        return new ResponseEntity<>(ip, HttpStatus.CREATED);
    }
}
