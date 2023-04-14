package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.Ipv4;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Ipv4
    @Column
    @JsonProperty(value = "ip")
    @NotNull(message = "Ip is blank")
    private String address;
}
