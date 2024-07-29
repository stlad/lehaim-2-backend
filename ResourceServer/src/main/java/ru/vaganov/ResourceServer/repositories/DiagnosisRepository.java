package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Diagnosis;

import java.util.LinkedList;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {

    LinkedList<Diagnosis> findAll();
}
