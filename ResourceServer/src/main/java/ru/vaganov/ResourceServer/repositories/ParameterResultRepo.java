package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.ParameterResult;

@Repository

public interface ParameterResultRepo extends JpaRepository<ParameterResult, Long> {
}
