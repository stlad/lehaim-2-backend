package ru.vaganov.lehaim.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.catalog.entity.Diagnosis;

import java.util.LinkedList;
import java.util.Optional;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {

    LinkedList<Diagnosis> findAll();

    Optional<Diagnosis> findByCode(String code);
}
