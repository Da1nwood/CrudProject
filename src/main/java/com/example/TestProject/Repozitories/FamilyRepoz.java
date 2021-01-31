package com.example.TestProject.Repozitories;

import com.example.TestProject.Entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepoz extends JpaRepository<Family, Long> {
    Optional<Family> findFamilyById(Long id);
}
