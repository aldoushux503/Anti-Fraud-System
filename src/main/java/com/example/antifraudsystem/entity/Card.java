package com.example.antifraudsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.LuhnCheck;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stolen_cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @JsonProperty(value = "number")
    @NotNull(message = "Card number is null")
    @LuhnCheck(message = "Card number has wrong format")
    private String number;
}
