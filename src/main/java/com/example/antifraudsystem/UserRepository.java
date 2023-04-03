package com.example.antifraudsystem;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class UserRepository {
    public ConcurrentMap<String, User> map = new ConcurrentHashMap<>();

    public User findByUsername(String username) {
        return map.get(username);
    }

    public void save(User user) {
        map.put(user.getUsername(), user);
    }

}


