package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    void deleteByUsernameIgnoreCase(String username);
}


