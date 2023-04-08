package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name = "roles")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole name;

    public Role(UserRole role) {
        this.name = role;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
