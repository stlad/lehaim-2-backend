package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
}
