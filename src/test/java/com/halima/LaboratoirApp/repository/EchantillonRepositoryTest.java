package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.LaboratoirAppApplication;
import com.halima.LaboratoirApp.model.entity.Analyse;
import com.halima.LaboratoirApp.model.entity.Echantillon;
import com.halima.LaboratoirApp.model.enums.AnalyseResultat;
import com.halima.LaboratoirApp.model.enums.StatutEchantillon;
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
class EchantillonRepositoryTest {

    @Autowired
    EchantillonRepository echantillonRepository;
    AnalyseRepository analyseRepository;

    Echantillon testEchantillon;
    Analyse testAnalyse;

    @BeforeEach
    void setUp() {
        // Create an Echantillon entity to test
        testEchantillon = new Echantillon();
        testEchantillon.setDateEchantillon(LocalDate.now());
        testEchantillon.setStatut(StatutEchantillon.EnCours);
        testEchantillon = echantillonRepository.save(testEchantillon);

        // Create an Analyse entity associated with the Echantillon
        testAnalyse = new Analyse();
        testAnalyse.setDateAnalyse(LocalDate.now());
        testAnalyse.setDateFinAnalyse(LocalDate.now().plusDays(7));
        testAnalyse.setAnalyseResultat(AnalyseResultat.Normal);
        testAnalyse.setCommentaire("Test Comment");
        testAnalyse.setEchantillon(testEchantillon);  // Set the Echantillon for the Analyse
        testAnalyse = analyseRepository.save(testAnalyse);
    }

    @Test
    void shouldCreateEchantillon() {
        Echantillon createdEchantillon = echantillonRepository.findById(testEchantillon.getEchantillonId()).orElse(null);
        assertThat(createdEchantillon).isNotNull();
    }

    @Test
    void shouldUpdateEchantillon() {
        Echantillon existingEchantillon = echantillonRepository.findById(testEchantillon.getEchantillonId()).orElse(null);
        existingEchantillon.setStatut(StatutEchantillon.Termine);

        echantillonRepository.save(existingEchantillon);

        Echantillon updatedEchantillon = echantillonRepository.findById(existingEchantillon.getEchantillonId()).orElse(null);

        assertThat(updatedEchantillon).isNotNull();
        assertThat(updatedEchantillon.getStatut()).isEqualTo(StatutEchantillon.Termine);
    }

    @Test
    void shouldFindEchantillonById() {
        Echantillon foundEchantillon = echantillonRepository.findById(testEchantillon.getEchantillonId()).orElse(null);

        assertThat(foundEchantillon).isNotNull();
        assertThat(foundEchantillon.getDateEchantillon()).isEqualTo(LocalDate.now());
    }

    @Test
    void shouldDeleteEchantillon() {
        echantillonRepository.deleteById(testEchantillon.getEchantillonId());
        Analyse deletedAnalyse = analyseRepository.findById(testAnalyse.getAnalyseId()).orElse(null);
        assertThat(deletedAnalyse).isNull();
    }

    @AfterEach
    void tearDown() {
        Echantillon echantillontoBeDeleted = echantillonRepository.findById(testEchantillon.getEchantillonId()).orElse(null);
        if (echantillontoBeDeleted != null) {
            echantillonRepository.deleteById(testEchantillon.getEchantillonId());
        }
    }
}
