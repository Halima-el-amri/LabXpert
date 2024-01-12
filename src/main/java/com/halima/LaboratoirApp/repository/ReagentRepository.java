package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.model.entity.Reagent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReagentRepository extends JpaRepository<Reagent,Long> {
}
