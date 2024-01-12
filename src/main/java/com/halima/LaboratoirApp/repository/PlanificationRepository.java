package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.model.entity.Planification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanificationRepository extends JpaRepository<Planification,Long> {
}
