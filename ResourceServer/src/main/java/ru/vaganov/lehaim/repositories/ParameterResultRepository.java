package ru.vaganov.lehaim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.models.ParameterResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParameterResultRepository extends JpaRepository<ParameterResult, Long> {

    List<ParameterResult> findByAttachedTest(OncologicalTest test);

    List<ParameterResult> findByAttachedTest_Id(Long test);

    Optional<ParameterResult> findByAttachedTest_PatientOwner_IdAndAttachedTest_IdAndParameter_Id(
            UUID ownerId, Long testId, Long parameterId);

}
