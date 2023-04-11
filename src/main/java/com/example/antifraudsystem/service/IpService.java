package com.example.antifraudsystem.service;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpService {

    InetAddressValidator validator = InetAddressValidator.getInstance();


    public void addSuspiciousIpToDataBase()  {

    }
}
