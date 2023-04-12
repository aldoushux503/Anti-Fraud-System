package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.Ip;
import com.example.antifraudsystem.repository.IpRepository;
import lombok.extern.java.Log;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
            LOGGER.error("Invalid IP address format {}", ipAddress);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (ipRepository.existsByAddress(ipAddress)) {
            LOGGER.error("IP already exists {}", ipAddress);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        LOGGER.info("Saving IP to database {}", ipAddress);
        ipRepository.save(ip);
        return new ResponseEntity<>(ip, HttpStatus.CREATED);
    }


    public ResponseEntity<?> deleteSuspiciousIpFromDataBase(String ipAddress) {
        if (!VALIDATOR.isValidInet4Address(ipAddress)) {
            LOGGER.error("Invalid IP address format {}", ipAddress);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Ip> ip = ipRepository.findIpByAddress(ipAddress);

        if (ip.isEmpty()) {
            LOGGER.error("IP address not found {}", ipAddress);
        }

        LOGGER.info("Deleting IP from database {}", ipAddress);
        ipRepository.delete(ip.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Ip> getAllSuspiciousIp() {
        return ipRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
