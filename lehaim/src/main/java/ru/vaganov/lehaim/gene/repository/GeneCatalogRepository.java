package ru.vaganov.lehaim.gene.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.gene.entity.Gene;

@Repository
public interface GeneCatalogRepository extends JpaRepository<Gene, Long> {
}
