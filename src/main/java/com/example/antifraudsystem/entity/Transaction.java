package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.Ipv4;
import com.example.antifraudsystem.enums.RegionCode;
import com.example.antifraudsystem.enums.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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

    @Ipv4
    @Column
    @NotNull(message = "Ip address is null")
    private String ip;

    @Column
    @NotNull(message = "Card number is null")
    @LuhnCheck(message = "Card number has wrong format")
    private String number;

    @Column
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Region code is null")
    private RegionCode region;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Date is null")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Past
    private LocalDateTime date;

    @Column
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus result;

    @Column
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus feedback;

    public String getFeedback() {
        return feedback == null ? "" : feedback.name();
    }
}
