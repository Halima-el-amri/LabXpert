package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.LaboratoirAppApplication;
import com.halima.LaboratoirApp.model.entity.Analyse;
import com.halima.LaboratoirApp.model.entity.Echantillon;
import com.halima.LaboratoirApp.model.entity.Planification;
import com.halima.LaboratoirApp.model.entity.User;
import com.halima.LaboratoirApp.model.enums.AnalyseResultat;
import com.halima.LaboratoirApp.model.enums.Role;
import com.halima.LaboratoirApp.repository.AnalyseRepository;
import com.halima.LaboratoirApp.repository.EchantillonRepository;
import com.halima.LaboratoirApp.repository.PlanificationRepository;
import com.halima.LaboratoirApp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.checkerframework.checker.nullness.Opt.orElse;

@SpringJUnitConfig(LaboratoirAppApplication.class)
@SpringBootTest
class AnalyseRepositoryTest {

    @Autowired
    AnalyseRepository analyseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlanificationRepository planificationRepository;

    @Autowired
    EchantillonRepository echantillonRepository;

    Analyse testAnalyse;
    User testUser;
    Planification testPlanification;
    Echantillon testEchantillon;

    @BeforeEach
    void setUp() {
        // Create a User entity to associate with the Analyse entity
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
        testUser.setRole(Role.TECHNICIEN);
        testUser.setPersonalInfo("informations");
        testUser = userRepository.save(testUser);

        // Create a Planification entity to associate with the Analyse entity
        testPlanification = new Planification();
        testPlanification.setDateHeureDebut(LocalDate.now().atStartOfDay());
        testPlanification.setDateHeureFin(LocalDate.now().plusDays(14).atStartOfDay());
//        testPlanification.setTechnicien(testUser);  // Set the user for the planification
        testPlanification = planificationRepository.save(testPlanification);

        // Create an Echantillon entity to associate with the Analyse entity
        testEchantillon = new Echantillon();
        testEchantillon = echantillonRepository.save(testEchantillon);

        // Create an Analyse entity to test
        testAnalyse = new Analyse();
        testAnalyse.setDateAnalyse(LocalDate.now());
        testAnalyse.setDateFinAnalyse(LocalDate.now().plusDays(7));
        testAnalyse.setAnalyseResultat(AnalyseResultat.Normal);
        testAnalyse.setCommentaire("Test Comment");
        testAnalyse.setTechnicien(testUser);
        testAnalyse.setPlanification(testPlanification);  // Set the planification for the analyse
        testAnalyse.setEchantillon(testEchantillon);
        testAnalyse = analyseRepository.save(testAnalyse);
    }

    @Test
    void shouldCreateAnalyse() {
        Analyse createdAnalyse = analyseRepository.findById(testAnalyse.getAnalyseId()).orElse(null);
        assertThat(createdAnalyse).isNotNull();
    }

    @Test
    void shouldUpdateAnalyse() {
        Analyse existingAnalyse = analyseRepository.findById(testAnalyse.getAnalyseId()).orElse(null);
        existingAnalyse.setCommentaire("Updated Comment");

        analyseRepository.save(existingAnalyse);

        Analyse updatedAnalyse = analyseRepository.findById(existingAnalyse.getAnalyseId()).orElse(null);

        assertThat(updatedAnalyse).isNotNull();
        assertThat(updatedAnalyse.getCommentaire()).isEqualTo("Updated Comment");
    }

    @Test
    void shouldFindAnalyseById() {
        Analyse foundAnalyse = analyseRepository.findById(testAnalyse.getAnalyseId()).orElse(null);

        assertThat(foundAnalyse).isNotNull();
        assertThat(foundAnalyse.getDateAnalyse()).isEqualTo(LocalDate.now());
    }

    @Test
    void shouldDeleteAnalyse() {
        analyseRepository.deleteById(testAnalyse.getAnalyseId());
        planificationRepository.deleteById(testPlanification.getPlanificationId());
        userRepository.deleteById(testUser.getId());
        echantillonRepository.deleteById(testEchantillon.getEchantillonId());

        Analyse deletedAnalyse = analyseRepository.findById(testAnalyse.getAnalyseId()).orElse(null);
        assertThat(deletedAnalyse).isNull();
    }

    @AfterEach
    void tearDown() {
        if (analyseRepository.findById(testAnalyse.getAnalyseId()).orElse(null) != null){
            analyseRepository.deleteById(testAnalyse.getAnalyseId());
            planificationRepository.deleteById(testPlanification.getPlanificationId());
            userRepository.deleteById(testUser.getId());
            echantillonRepository.deleteById(testEchantillon.getEchantillonId());
        }

    }
}


