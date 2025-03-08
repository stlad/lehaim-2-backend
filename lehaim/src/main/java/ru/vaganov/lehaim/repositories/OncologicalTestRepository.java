package ru.vaganov.lehaim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.patient.entity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OncologicalTestRepository extends JpaRepository<OncologicalTest, Long> {
    List<OncologicalTest> findByPatientOwnerOrderByTestDateDesc(Patient owner);

    List<OncologicalTest> findByPatientOwner_IdOrderByTestDateDesc(UUID ownerId);

    Optional<OncologicalTest> findByPatientOwner_IdAndTestDate(UUID ownerId, LocalDate testDate);

    List<OncologicalTest> findAllByPatientOwner_IdAndTestDateBefore(UUID ownerId, LocalDate date);
}
