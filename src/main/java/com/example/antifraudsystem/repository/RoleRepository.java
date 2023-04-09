package com.example.antifraudsystem.repository;

import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(UserRole name);

    boolean isRoleExistsByName(UserRole name);
}
