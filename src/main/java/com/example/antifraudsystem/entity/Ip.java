package com.example.antifraudsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity(name = "ip_addresses")
@NoArgsConstructor
public class Ip {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column
    private String value;
}
