package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findUserByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
}


