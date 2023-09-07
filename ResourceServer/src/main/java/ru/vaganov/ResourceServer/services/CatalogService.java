package ru.vaganov.ResourceServer.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private CatalogRepo catalogRepo;

    @Autowired
    public CatalogService(CatalogRepo catalogRepo) {
        this.catalogRepo = catalogRepo;
    }

    public Parameter save(Parameter param){
        Parameter savedParam = null ;
        try{
            savedParam = catalogRepo.save(param);
        }catch (DataIntegrityViolationException e){
            savedParam =  catalogRepo.findByNameAndAdditionalName(param.getName(), param.getAdditionalName());
        }
        return savedParam;
    }

    public void delete(Parameter parameter){
        catalogRepo.delete(parameter);
    }

    public List<Parameter> findByResearchType(Parameter.ResearchType type){
        return catalogRepo.findByResearchTypeOrderById(type);
    }
    public List<Parameter> findAll(){
        return catalogRepo.findAll();
    }

    public Parameter findById(Long id){
        return catalogRepo.findById(id).orElse(null);
    }
}
