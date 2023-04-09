package com.example.antifraudsystem.service;

import com.example.antifraudsystem.UserDetailsImpl;
import com.example.antifraudsystem.dto.UserDto;
import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.repository.RoleRepository;
import com.example.antifraudsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUsername())) {
                return new UserDetailsImpl(user);
            }
        }
        throw new UsernameNotFoundException("Not found: " + username);
    }

    public User createNewUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());

        // Check for existing users
        if (user != null) {
            return null;
        }

        // The first registered user should receive the ADMINISTRATOR role; the rest — MERCHANT.
        UserRole roleName = UserRole.ADMINISTRATOR;
        if (userRepository.count() != 0) {
            roleName = UserRole.MERCHANT;
        }
        Role role = roleRepository.findByName(roleName);

        User newUser = new User(userDto.getName(), userDto.getUsername(), userDto.getPassword(), role);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.setAccountNonLocked();
        userRepository.save(newUser);

        return newUser;
    }

    public List<User> showAllUsers() {
        List<User> res = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            res.add(user);
        }

        return res;
    }


    public User deleteUser(String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            userRepository.delete(user);
            return user;
        }

        return null;
    }
}
