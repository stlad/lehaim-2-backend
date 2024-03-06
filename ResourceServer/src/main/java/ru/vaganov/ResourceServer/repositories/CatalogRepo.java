package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Parameter;

import java.util.List;
import java.util.Set;


@Repository
public interface CatalogRepo extends JpaRepository<Parameter,Long> {

    public Parameter findByName(String name);
    public Parameter findByAdditionalName(String abbreviation);

    public Parameter findByNameAndAdditionalName(String name, String adName);

    public List<Parameter> findByResearchTypeOrderById(Parameter.ResearchType type);

    public Boolean existsByNameAndAdditionalName(String name, String addName);
}
