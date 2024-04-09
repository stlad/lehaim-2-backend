package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Diagnosis;

@Repository
public interface DiagnosisRepo extends JpaRepository<Diagnosis, Integer> {


}
