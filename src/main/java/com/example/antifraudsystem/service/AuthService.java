package com.example.antifraudsystem.service;

import com.example.antifraudsystem.dto.UserDto;
import com.example.antifraudsystem.dto.UserLockDto;
import com.example.antifraudsystem.dto.UserRoleDto;
import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.entity.User;
import com.example.antifraudsystem.enums.ActivityOperation;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.repository.RoleRepository;
import com.example.antifraudsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
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
        newUser.setAccountNonLocked(false);
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

    public ResponseEntity<?> changeUserRole(UserRoleDto userRoleDto) {
        User user = userRepository.findByUsername(userRoleDto.username());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (!Objects.equals(userRoleDto.role(), UserRole.MERCHANT.name())
                && !Objects.equals(userRoleDto.role(), UserRole.SUPPORT.name())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (Objects.equals(user.getRole(), userRoleDto.role())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Role role = roleRepository.findByName(UserRole.valueOf(userRoleDto.role()));
        user.setRole(role);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> changeLockStatus(UserLockDto userLockDto) {
        User user = userRepository.findByUsername(userLockDto.username());
        boolean status = !Objects.equals(userLockDto.operation(), ActivityOperation.LOCK);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (Objects.equals(user.getRole(), UserRole.ADMINISTRATOR.name()) ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setAccountNonLocked(status);
        userRepository.save(user);
        String res = String.format("User %s %s!", userLockDto.username(), userLockDto.operation().toString().toLowerCase());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
