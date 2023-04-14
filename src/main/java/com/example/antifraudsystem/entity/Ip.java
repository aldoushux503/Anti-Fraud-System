package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.Ipv4;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "suspicious_ips")
public class Ip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @JsonProperty(value = "ip")
    @NotBlank(message = "Ip is blank")
    @Ipv4(message = "Ip address has wrong format")
    private String address;
}
