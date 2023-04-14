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
import org.hibernate.validator.constraints.LuhnCheck;
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

    @Column
    @Min(value = 1, message = "Amount less than or equal to zero")
    private long amount;

    @Column
    @NotBlank
    private String ip;

    @Column
    @NotBlank(message = "Card number is null")
    @LuhnCheck(message = "Card number has wrong format")
    private String number;

    @Column
    @NotBlank
    @Enumerated(value = EnumType.STRING)
    private RegionCode region;

    @Column
    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date date;
}
