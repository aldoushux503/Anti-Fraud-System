package com.example.antifraudsystem.entity;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name = "roles")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    public Role(String role) {
        this.name = role;
    }
}
