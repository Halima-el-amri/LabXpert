package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.LaboratoirAppApplication;
import com.halima.LaboratoirApp.model.entity.Analyse;
import com.halima.LaboratoirApp.model.entity.Planification;
import com.halima.LaboratoirApp.model.entity.User;
import com.halima.LaboratoirApp.model.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(LaboratoirAppApplication.class)
@SpringBootTest
class PlanificationRepositoryTest {

    @Autowired
    private PlanificationRepository planificationRepository;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Autowired
    private UserRepository userRepository;

    private Planification testPlanification;
    private Analyse testAnalyse;
    private User testTechnicien;

    @BeforeEach
    void setUp() {
        // Create a User entity
        testTechnicien = new User();
        testTechnicien.setUsername("testTechnicien");
        testTechnicien.setPassword("password123");
        testTechnicien.setRole(Role.TECHNICIEN);
        testTechnicien.setPersonalInfo("Test Technicien Info");
        testTechnicien = userRepository.save(testTechnicien);

        // Create an Analyse entity to associate with the Planification entity
        testAnalyse = new Analyse();
        testAnalyse.setDateAnalyse(LocalDate.from(LocalDateTime.now().minusDays(7)));
        testAnalyse.setDateFinAnalyse(LocalDate.from(LocalDateTime.now().plusDays(7)));
        // Set other properties for the testAnalyse as needed
        testAnalyse = analyseRepository.save(testAnalyse);

        // Create a Planification entity to test
        testPlanification = new Planification();
        testPlanification.setAnalyse(testAnalyse);
        testPlanification.setTechnicien(testTechnicien);
        testPlanification.setDateHeureDebut(LocalDateTime.now());
        testPlanification.setDateHeureFin(LocalDateTime.now().plusDays(14));
        testPlanification = planificationRepository.save(testPlanification);
    }

    @Test
    void shouldCreatePlanification() {
        Planification createdPlanification = planificationRepository.findById(testPlanification.getPlanificationId()).orElse(null);
        assertThat(createdPlanification).isNotNull();
    }

    @Test
    void shouldUpdatePlanification() {
        Planification existingPlanification = planificationRepository.findById(testPlanification.getPlanificationId()).orElse(null);
        existingPlanification.setDateHeureDebut(LocalDateTime.now().plusDays(1));

        planificationRepository.save(existingPlanification);

        Planification updatedPlanification = planificationRepository.findById(existingPlanification.getPlanificationId()).orElse(null);

        assertThat(updatedPlanification).isNotNull();
        assertThat(updatedPlanification.getDateHeureDebut()).isEqualTo(LocalDateTime.now().plusDays(1));
    }

    @Test
    void shouldFindPlanificationById() {
        Planification foundPlanification = planificationRepository.findById(testPlanification.getPlanificationId()).orElse(null);

        assertThat(foundPlanification).isNotNull();
        assertThat(foundPlanification.getDateHeureDebut()).isEqualTo(LocalDateTime.now());
    }

    @Test
    void shouldDeletePlanification() {
        planificationRepository.deleteById(testPlanification.getPlanificationId());
        Planification deletedPlanification = planificationRepository.findById(testPlanification.getPlanificationId()).orElse(null);
        assertThat(deletedPlanification).isNull();
    }

    @AfterEach
    void tearDown() {
        if (planificationRepository.findById(testPlanification.getPlanificationId()).orElse(null) != null) {
            planificationRepository.deleteById(testPlanification.getPlanificationId());
            analyseRepository.deleteById(testAnalyse.getAnalyseId());
            userRepository.deleteById(testTechnicien.getId());
        }
    }
}
