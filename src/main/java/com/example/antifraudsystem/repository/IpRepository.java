package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.Ip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpRepository extends JpaRepository<Ip, Long> {

    Optional<Ip> findIpByAddress(String ipAddress);

    boolean existsByAddress(String ipAddress);
}
