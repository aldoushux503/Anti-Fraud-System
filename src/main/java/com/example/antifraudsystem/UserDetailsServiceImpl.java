package com.example.antifraudsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return new UserDetailsImpl(user);
            }
        }
        throw new UsernameNotFoundException("Not found: " + username);
    }
}
