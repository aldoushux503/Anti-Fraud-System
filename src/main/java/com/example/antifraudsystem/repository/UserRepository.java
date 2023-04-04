package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Long> {

}


