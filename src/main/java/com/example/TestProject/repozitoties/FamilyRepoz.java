package com.example.TestProject.repozitoties;

import com.example.TestProject.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepoz extends JpaRepository<Family, Long> {
    Optional<Family> findFamilyById(Long id);
}
