package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.enums.RegionCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(1)
    @Column
    private long amount;

    @NotBlank
    @Column
    private String ip;

    @NotBlank
    @Column
    private String number;

    @Column
    @Enumerated(value = EnumType.STRING)
    private RegionCode region;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;
}
