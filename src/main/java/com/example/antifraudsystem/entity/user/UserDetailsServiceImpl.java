package com.example.antifraudsystem.entity.user;

import com.example.antifraudsystem.entity.user.UserDetailsImpl;
import com.example.antifraudsystem.entity.user.User;
import com.example.antifraudsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsernameIgnoreCase(username);

        if (user.isPresent()) {
            return new UserDetailsImpl(user.get());
        }

        throw new UsernameNotFoundException("Not found: " + username);
    }
}
