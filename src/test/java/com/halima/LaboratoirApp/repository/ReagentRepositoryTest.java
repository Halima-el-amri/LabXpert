package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.LaboratoirAppApplication;
import com.halima.LaboratoirApp.model.entity.Reagent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(LaboratoirAppApplication.class)
@SpringBootTest
class ReagentRepositoryTest {

    @Autowired
    private ReagentRepository reagentRepository;

    private Reagent testReagent;

    @BeforeEach
    void setUp() {
        // Create a Reagent entity to test
        testReagent = new Reagent();
        testReagent.setName("Test Reagent");
        testReagent.setDescription("Test Description");
        testReagent.setStockQuantity(100);
        testReagent.setExpirationDate(LocalDate.now().plusMonths(6));
        testReagent.setSupplier("Test Supplier");
        testReagent = reagentRepository.save(testReagent);
    }

    @Test
    void shouldCreateReagent() {
        Reagent createdReagent = reagentRepository.findById(testReagent.getId()).orElse(null);
        assertThat(createdReagent).isNotNull();
    }

    @Test
    void shouldUpdateReagent() {
        Reagent existingReagent = reagentRepository.findById(testReagent.getId()).orElse(null);
        existingReagent.setDescription("Updated Description");

        reagentRepository.save(existingReagent);

        Reagent updatedReagent = reagentRepository.findById(existingReagent.getId()).orElse(null);

        assertThat(updatedReagent).isNotNull();
        assertThat(updatedReagent.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    void shouldFindReagentById() {
        Reagent foundReagent = reagentRepository.findById(testReagent.getId()).orElse(null);

        assertThat(foundReagent).isNotNull();
        assertThat(foundReagent.getName()).isEqualTo("Test Reagent");
    }

    @Test
    void shouldDeleteReagent() {
        reagentRepository.deleteById(testReagent.getId());
        Reagent deletedReagent = reagentRepository.findById(testReagent.getId()).orElse(null);
        assertThat(deletedReagent).isNull();
    }

    @AfterEach
    void tearDown() {
        Reagent reagentToBeDeleted = reagentRepository.findById(testReagent.getId()).orElse(null);
        if (reagentToBeDeleted != null) {
            reagentRepository.deleteById(testReagent.getId());
        }
    }
}
