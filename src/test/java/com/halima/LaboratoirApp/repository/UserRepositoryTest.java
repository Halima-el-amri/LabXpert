package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.LaboratoirAppApplication;
import com.halima.LaboratoirApp.model.entity.User;
import com.halima.LaboratoirApp.model.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(LaboratoirAppApplication.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create a User entity to test
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
        testUser.setRole(Role.TECHNICIEN);
        testUser.setPersonalInfo("Test Personal Info");
        testUser = userRepository.save(testUser);
    }

    @Test
    void shouldCreateUser() {
        User createdUser = userRepository.findById(testUser.getId()).orElse(null);
        assertThat(createdUser).isNotNull();
    }

    @Test
    void shouldUpdateUser() {
        User existingUser = userRepository.findById(testUser.getId()).orElse(null);
        existingUser.setPersonalInfo("Updated Personal Info");

        userRepository.save(existingUser);

        User updatedUser = userRepository.findById(existingUser.getId()).orElse(null);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getPersonalInfo()).isEqualTo("Updated Personal Info");
    }

    @Test
    void shouldFindUserById() {
        User foundUser = userRepository.findById(testUser.getId()).orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testUser");
    }

    @Test
    void shouldDeleteUser() {
        userRepository.deleteById(testUser.getId());
        User deletedUser = userRepository.findById(testUser.getId()).orElse(null);
        assertThat(deletedUser).isNull();
    }

    @AfterEach
    void tearDown() {
        User userToBeDeleted = userRepository.findById(testUser.getId()).orElse(null);
        if (userToBeDeleted != null) {
            userRepository.deleteById(testUser.getId());
        }
    }
}
