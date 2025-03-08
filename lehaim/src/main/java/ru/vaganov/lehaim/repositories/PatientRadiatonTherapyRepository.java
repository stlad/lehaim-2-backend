package ru.vaganov.lehaim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;

import java.util.UUID;

public interface PatientRadiatonTherapyRepository extends JpaRepository<PatientRadiationTherapy, UUID> {
}
