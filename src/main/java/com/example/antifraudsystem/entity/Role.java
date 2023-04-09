package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@Entity(name = "roles")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(UserRole role) {
        this.name = role;
    }

    @Override
    public String toString() {
        return name.toString();
    }


}
