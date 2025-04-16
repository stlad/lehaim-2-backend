package ru.vaganov.lehaim.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.exceptions.PatientNotFoundException;
import ru.vaganov.lehaim.patient.entity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    @Query("SELECT p FROM Patient p " +
            "WHERE UPPER(p.name) LIKE UPPER(:firstname) " +
            "AND UPPER(p.lastname) LIKE UPPER(:lastname) " +
            "AND UPPER(p.patronymic) LIKE UPPER(:patronymic) " +
            "AND p.birthdate = :birthdate")
    List<Patient> findByNameAndLastnameAndPatronymicAndBirthdate(String firstname, String lastname,
                                                                 String patronymic, LocalDate birthdate);

    @Query("SELECT p FROM Patient p " +
            "WHERE UPPER(p.name) LIKE UPPER(:firstname) " +
            "AND UPPER(p.lastname) LIKE UPPER(:lastname) " +
            "AND p.birthdate = :birthdate")
    List<Patient> findByNameAndLastnameAndBirthdate(String firstname, String lastname, LocalDate birthdate);

    default Optional<Patient> findSinglePatient(String firstname, String lastname, String patronymic, LocalDate birthdate) {
        if (patronymic == null) {
            return findByNameAndLastnameAndBirthdate(firstname, lastname, birthdate).stream()
                    .findAny();
        } else {
            return findByNameAndLastnameAndPatronymicAndBirthdate(firstname, lastname, patronymic, birthdate).stream()
                    .findAny();
        }
    }
}
