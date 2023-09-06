package ru.vaganov.ResourceServer.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;

@Service
public class CatalogService {

    private CatalogRepo catalogRepo;

    @Autowired
    public CatalogService(CatalogRepo catalogRepo) {
        this.catalogRepo = catalogRepo;
    }

    public Parameter save(Parameter param){
        return catalogRepo.save(param);
    }

    public void delete(Parameter parameter){
        catalogRepo.delete(parameter);
    }
}
