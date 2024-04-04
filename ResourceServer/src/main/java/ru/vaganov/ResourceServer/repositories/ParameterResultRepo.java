package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface ParameterResultRepo extends JpaRepository<ParameterResult, Long> {

    public List<ParameterResult> findByAttachedTest(OncologicalTest test);
    public List<ParameterResult> findByAttachedTest_Id(Long test);
    public Optional<ParameterResult> findByAttachedTest_PatientOwner_IdAndAttachedTest_TestDateAndParameter_Id(
            Long ownerId, LocalDate testDate, Long parameterId);
}
