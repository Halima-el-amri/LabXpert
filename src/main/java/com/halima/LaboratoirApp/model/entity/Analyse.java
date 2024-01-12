package com.halima.LaboratoirApp.model.entity;

import com.halima.LaboratoirApp.model.enums.AnalyseResultat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "analyses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Analyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analyseId;

    @ManyToOne
    @JoinColumn(name = "echantillon_id")
    private Echantillon echantillon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User technicien;

    @Column(name = "date_analyse")
    private LocalDate dateAnalyse;

    @Column(name = "date_fin_analyse")
    private LocalDate dateFinAnalyse;

    @Enumerated(EnumType.STRING)
    @Column(name = "analyseResultat")
    private AnalyseResultat analyseResultat;

    @ManyToOne
    @JoinColumn(name = "planification_id")
    private Planification planification;

    @Column(name = "commentaire")
    private String commentaire;
}