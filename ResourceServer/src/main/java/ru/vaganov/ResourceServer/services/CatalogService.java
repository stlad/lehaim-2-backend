package ru.vaganov.ResourceServer.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.config.DataLoadingConfig;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private CatalogRepo catalogRepo;

    Logger logger = LoggerFactory.getLogger(CatalogService.class);
    @Autowired
    public CatalogService(CatalogRepo catalogRepo) {
        this.catalogRepo = catalogRepo;
    }

    public Parameter save(Parameter param){
        Parameter savedParam = null ;
        try{
            savedParam = catalogRepo.save(param);
            logger.debug("param" + param.toString() + "saved");
        }catch (DataIntegrityViolationException e){
            savedParam =  catalogRepo.findByNameAndAdditionalName(param.getName(), param.getAdditionalName());
            logger.debug("param" + param.toString() + "already exist");
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
