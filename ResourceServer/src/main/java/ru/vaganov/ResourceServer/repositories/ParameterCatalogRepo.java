package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vaganov.ResourceServer.models.Parameter;


@Repository
public interface ParameterCatalogRepo extends JpaRepository<Parameter,Long> {

    public Parameter findByName(String name);
    public Parameter findByAbbreviation(String abbreviation);


}
