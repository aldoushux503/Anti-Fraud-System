package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.Ip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRepository extends JpaRepository<Ip, Long> {

}
