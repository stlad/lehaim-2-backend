package ru.vaganov.lehaim.repositories.genes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.models.genes.GeneValue;

import java.util.List;
import java.util.UUID;

@Repository
public interface GeneValuesRepository extends JpaRepository<GeneValue, Long> {

    List<GeneValue> findByPatient_Id(UUID id);

}
