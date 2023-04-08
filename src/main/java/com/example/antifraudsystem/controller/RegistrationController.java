package com.example.antifraudsystem.controller;

import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.dto.UserDto;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.repository.RoleRepository;
import com.example.antifraudsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    private final PasswordEncoder encoder;

    @Autowired
    public RegistrationController(UserRepository userRepo, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepository;
        this.encoder = encoder;
    }

    @PostMapping("/api/auth/user")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        if (userDto.getName() == null || userDto.getUsername() == null || userDto.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User newUser = new User(userDto.getName(), userDto.getUsername(), userDto.getPassword(), UserRole.ADMINISTRATOR);

        Iterable<User> users = userRepo.findAll();

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(newUser.getUsername())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepo.save(newUser);

        Iterable<Role> roles =  roleRepo.findAll();
        for (Role role : roles) {
            System.out.println(role);
        }


        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
