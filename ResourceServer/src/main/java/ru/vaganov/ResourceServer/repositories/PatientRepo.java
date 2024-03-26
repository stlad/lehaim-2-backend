package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
    Optional<Patient> findByNameAndLastnameAndPatronymicAndBirthdate(String firstname, String lastname,
                                                                     String patronymic, LocalDate birthdate);
}
