package com.halima.LaboratoirApp.model.entity;

import com.halima.LaboratoirApp.model.enums.StatutEchantillon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Echantillon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long echantillonId;

    @Column(name = "date_echantillon")
    private LocalDate dateEchantillon;

    @OneToMany(mappedBy = "echantillon")
    private List<Analyse> analyses;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutEchantillon statut;
}
