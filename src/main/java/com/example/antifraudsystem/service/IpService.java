package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.Ip;
import com.example.antifraudsystem.repository.IpRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IpService {

    private final Logger LOGGER = LoggerFactory.getLogger(IpService.class);
    private IpRepository ipRepository;

    @Autowired
    public IpService(IpRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public ResponseEntity<?> addSuspiciousIpToDataBase(Ip ip)  {
        String ipAddress = ip.getAddress();

        if (ipRepository.existsByAddress(ipAddress)) {
            LOGGER.error("IP already exists {}", ipAddress);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        LOGGER.info("Saving IP to database {}", ipAddress);
        ipRepository.save(ip);
        return new ResponseEntity<>(ip, HttpStatus.OK);
    }


    public ResponseEntity<?> deleteSuspiciousIpFromDataBase(String ipAddress) {
        Optional<Ip> ip = ipRepository.findIpByAddress(ipAddress);

        if (ip.isEmpty()) {
            LOGGER.error("IP address not found {}", ipAddress);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Deleting IP from database {}", ipAddress);
        ipRepository.delete(ip.get());

        String res = String.format("IP %s successfully removed!", ip.get().getAddress());

        return new ResponseEntity<>(Map.of("status", res), HttpStatus.OK);
    }

    public List<Ip> getAllSuspiciousIp() {
        return ipRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
