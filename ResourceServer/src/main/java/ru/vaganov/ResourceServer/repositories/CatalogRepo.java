package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Parameter;

import java.util.List;
import java.util.Set;


@Repository
public interface CatalogRepo extends JpaRepository<Parameter,Long> {

    @Query("SELECT p FROM Parameter p "+
            "WHERE UPPER(p.name) LIKE UPPER(:name) "+
            "AND UPPER(p.additionalName) LIKE UPPER(:adName) "+
            "AND p.isActive = true ")
    public Parameter findByNameAndAdditionalName(String name, String adName);

    @Query("SELECT p FROM Parameter p "+
            "WHERE p.researchType = :type "+
            "AND p.isActive = true " +
            "ORDER BY p.id")
    public List<Parameter> findByResearchTypeOrderById(Parameter.ResearchType type);

}
