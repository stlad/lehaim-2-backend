package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;

import java.util.List;

@Repository

public interface ParameterResultRepo extends JpaRepository<ParameterResult, Long> {

    public List<ParameterResult> findByAttachedTest(OncologicalTest test);
}
