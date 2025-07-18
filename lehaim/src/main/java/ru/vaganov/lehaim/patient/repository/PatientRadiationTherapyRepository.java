package ru.vaganov.lehaim.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;

import java.util.Optional;
import java.util.UUID;

public interface PatientRadiationTherapyRepository extends JpaRepository<PatientRadiationTherapy, UUID> {

    Optional<PatientRadiationTherapy> findByPatient_Id(UUID patientId);
}
