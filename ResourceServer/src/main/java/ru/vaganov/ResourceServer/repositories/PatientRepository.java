package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    @Query("SELECT p FROM Patient p "+
            "WHERE UPPER(p.name) LIKE UPPER(:firstname) "+
            "AND UPPER(p.lastname) LIKE UPPER(:lastname) "+
            "AND UPPER(p.patronymic) LIKE UPPER(:patronymic) "+
            "AND p.birthdate = :birthdate")
    Optional<Patient> findByNameAndLastnameAndPatronymicAndBirthdate(String firstname, String lastname,
                                                                     String patronymic, LocalDate birthdate);
    @Query("SELECT p FROM Patient p "+
            "WHERE UPPER(p.name) LIKE UPPER(:firstname) "+
            "AND UPPER(p.lastname) LIKE UPPER(:lastname) "+
            "AND p.birthdate = :birthdate")
    Optional<Patient> findByNameAndLastnameAndBirthdate(String firstname, String lastname,LocalDate birthdate);
}
