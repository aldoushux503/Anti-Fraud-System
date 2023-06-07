package com.example.antifraudsystem.service;

import com.example.antifraudsystem.dto.UserLockDto;
import com.example.antifraudsystem.dto.UserRoleDto;
import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.entity.user.User;
import com.example.antifraudsystem.enums.ActivityOperation;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.repository.RoleRepository;
import com.example.antifraudsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
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

    public ResponseEntity<?> createNewUser(User user) {
        if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            log.error("User already exists {}", user);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Role role;
        user.setPassword(encoder.encode(user.getPassword()));

        // The first registered user should receive the ADMINISTRATOR role; the rest — MERCHANT.
        if (userRepository.count() == 0) {
            role = roleRepository.findByName(UserRole.ADMINISTRATOR);
            user.setRole(role);
            user.setAccountNonLocked(true);
        } else {
            role = roleRepository.findByName(UserRole.MERCHANT);
            user.setRole(role);
            user.setAccountNonLocked(false);
        }

        log.info("Saving user: {}", user);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }


    public ResponseEntity<?> deleteUser(String username) {
        Optional<User> u = userRepository.findUserByUsernameIgnoreCase(username);

        if (u.isPresent()) {
            userRepository.delete(u.get());
            log.info("User deleting {}", u);
            return new ResponseEntity<>(Map.of("username", username,
                    "status", "Deleted successfully!"), HttpStatus.OK);
        }
        log.error("User not found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> changeUserRole(UserRoleDto userRoleDto) {
        Optional<User> u = userRepository.findUserByUsernameIgnoreCase(userRoleDto.username());
        if (u.isEmpty()) {
            log.error("User not found {}", u);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = u.get();

         if (!Objects.equals(userRoleDto.role(), UserRole.MERCHANT.name())
                && !Objects.equals(userRoleDto.role(), UserRole.SUPPORT.name())) {
             log.error("User is not Merchant or Support {}", u);
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }

        if (Objects.equals(user.getRole().toString(), userRoleDto.role())) {
            log.error("User now has the same role {}", u);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Role role = roleRepository.findByName(UserRole.valueOf(userRoleDto.role()));
        log.info("Changing User role... {}", u);
        user.setRole(role);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> changeLockStatus(UserLockDto userLockDto) {
        Optional<User> u = userRepository.findUserByUsernameIgnoreCase(userLockDto.username());

        if (u.isEmpty()) {
            log.error("User not found {}", u);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = u.get();

        if (Objects.equals(user.getRole().toString(), UserRole.ADMINISTRATOR.name())) {
            log.error("Administrator can't be locked {}", u);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean status = !Objects.equals(userLockDto.operation(), ActivityOperation.LOCK);
        log.info("Changing lock status... {}", u);
        user.setAccountNonLocked(status);
        userRepository.save(user);
        String res = String.format("User %s %sed!", userLockDto.username(), userLockDto.operation().toString().toLowerCase());

        return new ResponseEntity<>(Map.of("status", res), HttpStatus.OK);
    }

}
