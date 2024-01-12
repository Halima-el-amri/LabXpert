package com.halima.LaboratoirApp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "report_period")
    private String reportPeriod;

    @Column(name = "statistical_data")
    private String statisticalData;

    @Column(name = "graph_data")
    private String graphData;

}
