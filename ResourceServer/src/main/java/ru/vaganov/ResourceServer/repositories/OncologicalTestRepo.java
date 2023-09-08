package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.OncologicalTest;

import java.time.LocalDate;


@Repository
public interface OncologicalTestRepo extends JpaRepository<OncologicalTest, Long> {


}
