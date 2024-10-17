package ru.vaganov.lehaim.repositories.genes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.models.genes.Gene;

@Repository
public interface GeneCatalogRepository extends JpaRepository<Gene, Long> {
}
