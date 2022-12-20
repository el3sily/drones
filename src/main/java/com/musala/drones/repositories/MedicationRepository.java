package com.musala.drones.repositories;

import com.musala.drones.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    Medication findByCode(String code);
}
