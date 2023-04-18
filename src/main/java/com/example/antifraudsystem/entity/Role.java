package com.example.antifraudsystem.entity;


import com.example.antifraudsystem.entity.user.User;
import com.example.antifraudsystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "roles")
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
