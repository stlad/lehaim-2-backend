package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface OncologicalTestRepo extends JpaRepository<OncologicalTest, Long> {
    List<OncologicalTest> findByPatientOwner(Patient owner);

}
