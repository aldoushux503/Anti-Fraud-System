package com.example.antifraudsystem.service;

import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.enums.UserRole;
import com.example.antifraudsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public void addAllRoles() {
        if (!roleRepository.isRoleExistsByName(UserRole.ADMINISTRATOR)
                || !roleRepository.isRoleExistsByName(UserRole.MERCHANT)
                || !roleRepository.isRoleExistsByName(UserRole.SUPPORT)) {
            Role admin = new Role(UserRole.ADMINISTRATOR);
            Role support = new Role(UserRole.MERCHANT);
            Role merchant = new Role(UserRole.SUPPORT);

            roleRepository.saveAll(List.of(admin, support, merchant));
        }
    }
}
