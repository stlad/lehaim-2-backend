package ru.vaganov.ResourceServer.services;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.mappers.ParameterMapper;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.dto.ParameterDTO;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;

import java.util.List;

@Service @Slf4j
@AllArgsConstructor
public class CatalogService {

    private CatalogRepo catalogRepo;
    private ParameterMapper parameterMapper;

    public Parameter save(Parameter param){
        Parameter savedParam = null ;
        try{
            savedParam = catalogRepo.save(param);
            log.debug("param" + param.toString() + "saved");
        }catch (DataIntegrityViolationException e){
            savedParam =  catalogRepo.findByNameAndAdditionalName(param.getName(), param.getAdditionalName());
            log.debug("param" + param.toString() + " already exist");
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
        return catalogRepo.findAllActive();
    }

    public Parameter findById(Long id){
        return catalogRepo.findById(id).orElse(null);
    }

    public ParameterDTO getDtoById(Long id){
        Parameter parameter = catalogRepo.findById(id).
            orElseThrow(()-> new EntityNotFoundException("НЕ удается найти параметр c id: "+id));
        return parameterMapper.toDto(parameter);
    }
}
