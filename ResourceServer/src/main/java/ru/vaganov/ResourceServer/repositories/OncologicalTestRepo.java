package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OncologicalTestRepo extends JpaRepository<OncologicalTest, Long> {
    List<OncologicalTest> findByPatientOwnerOrderByTestDateDesc(Patient owner);

    List<OncologicalTest> findByPatientOwner_IdOrderByTestDateDesc(UUID ownerId);
    Optional<OncologicalTest> findByPatientOwner_IdAndTestDate(UUID ownerId, LocalDate testDate);
    List<OncologicalTest> findAllByPatientOwner_IdAndTestDateBefore(UUID ownerId, LocalDate date);
}
