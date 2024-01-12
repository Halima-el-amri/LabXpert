package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.LaboratoirAppApplication;
import com.halima.LaboratoirApp.model.entity.StatisticalReport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(LaboratoirAppApplication.class)
@SpringBootTest
class StatisticalReportRepositoryTest {

    @Autowired
    private StatisticalReportRepository statisticalReportRepository;

    private StatisticalReport testStatisticalReport;

    @BeforeEach
    void setUp() {
        // Create a StatisticalReport entity to test
        testStatisticalReport = new StatisticalReport();
        testStatisticalReport.setReportType("Test Report Type");
        testStatisticalReport.setReportPeriod("Test Report Period");
        testStatisticalReport.setStatisticalData("Test Statistical Data");
        testStatisticalReport.setGraphData("Test Graph Data");
        testStatisticalReport = statisticalReportRepository.save(testStatisticalReport);
    }

    @Test
    void shouldCreateStatisticalReport() {
        StatisticalReport createdStatisticalReport = statisticalReportRepository.findById(testStatisticalReport.getId()).orElse(null);
        assertThat(createdStatisticalReport).isNotNull();
    }

    @Test
    void shouldUpdateStatisticalReport() {
        StatisticalReport existingStatisticalReport = statisticalReportRepository.findById(testStatisticalReport.getId()).orElse(null);
        existingStatisticalReport.setReportPeriod("Updated Report Period");

        statisticalReportRepository.save(existingStatisticalReport);

        StatisticalReport updatedStatisticalReport = statisticalReportRepository.findById(existingStatisticalReport.getId()).orElse(null);

        assertThat(updatedStatisticalReport).isNotNull();
        assertThat(updatedStatisticalReport.getReportPeriod()).isEqualTo("Updated Report Period");
    }

    @Test
    void shouldFindStatisticalReportById() {
        StatisticalReport foundStatisticalReport = statisticalReportRepository.findById(testStatisticalReport.getId()).orElse(null);

        assertThat(foundStatisticalReport).isNotNull();
        assertThat(foundStatisticalReport.getReportType()).isEqualTo("Test Report Type");
    }

    @Test
    void shouldDeleteStatisticalReport() {
        statisticalReportRepository.deleteById(testStatisticalReport.getId());
        StatisticalReport deletedStatisticalReport = statisticalReportRepository.findById(testStatisticalReport.getId()).orElse(null);
        assertThat(deletedStatisticalReport).isNull();
    }

    @AfterEach
    void tearDown() {
        StatisticalReport statisticalReportToBeDeleted = statisticalReportRepository.findById(testStatisticalReport.getId()).orElse(null);
        if (statisticalReportToBeDeleted != null) {
            statisticalReportRepository.deleteById(testStatisticalReport.getId());
        }
    }
}
