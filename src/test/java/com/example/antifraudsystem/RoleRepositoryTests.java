package com.example.antifraudsystem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.antifraudsystem.entity.Role;
import com.example.antifraudsystem.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired private RoleRepository repo;

    @Test
    public void testCreateRoles() {
        Role user = new Role("Admin");
        Role admin = new Role("Merch");
        Role customer = new Role("User");

        repo.saveAll(List.of(user, admin, customer));

        List<Role> listRoles = (List<Role>) repo.findAll();

        System.out.println(listRoles);
    }

}