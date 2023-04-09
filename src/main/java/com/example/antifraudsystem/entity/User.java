package com.example.antifraudsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String username;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean accountNonLocked;

    public User(String name, String username, String password, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
