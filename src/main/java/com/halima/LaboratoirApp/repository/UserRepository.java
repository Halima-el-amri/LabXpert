package com.halima.LaboratoirApp.repository;

import com.halima.LaboratoirApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
