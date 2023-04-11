package com.example.antifraudsystem.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ips")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Ip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @JsonProperty(value = "ip")
    private String address;
}
