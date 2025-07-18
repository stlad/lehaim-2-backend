package ru.vaganov.lehaim.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vaganov.lehaim.catalog.entity.Parameter;

import java.util.List;


@Repository
public interface ParameterCatalogRepository extends JpaRepository<Parameter, Long> {

    @Query("SELECT p FROM Parameter p " +
            "WHERE UPPER(p.name) LIKE UPPER(:name) " +
            "AND UPPER(p.additionalName) LIKE UPPER(:adName) " +
            "AND p.isActive = true ")
    Parameter findByNameAndAdditionalName(String name, String adName);

    @Query("SELECT p FROM Parameter p " +
            "WHERE p.researchType = :type " +
            "AND p.isActive = true " +
            "ORDER BY p.id")
    List<Parameter> findByResearchTypeOrderById(Parameter.ResearchType type);

    @Query("SELECT p FROM Parameter p WHERE p.isActive = true")
    List<Parameter> findAllActive();
}
