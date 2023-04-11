package com.example.antifraudsystem.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity(name = "ip_addresses")
@NoArgsConstructor
public class Ip {

    @Id
    private long id;

    @Column
    private String value;
}
