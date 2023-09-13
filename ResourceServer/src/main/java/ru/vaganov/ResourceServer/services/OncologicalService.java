package ru.vaganov.ResourceServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.repositories.OncologicalTestRepo;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepo;

import java.time.LocalDate;
import java.util.List;

@Service
public class OncologicalService {
    @Autowired
    ParameterResultRepo parameterResultRepo;
    @Autowired
    OncologicalTestRepo oncologicalTestRepo;

    public ParameterResult findResultById(Long id){
        return parameterResultRepo.findById(id).orElse(null);
    }

    public ParameterResult saveResult(ParameterResult result){
        return parameterResultRepo.save(result);
    }

    public void delete(ParameterResult result){
        parameterResultRepo.delete(result);
    }




    public OncologicalTest findTestById(Long id){
        return oncologicalTestRepo.findById(id).orElse(null);
    }

    public void delete(OncologicalTest test){
        oncologicalTestRepo.delete(test);
    }

    public OncologicalTest saveTest(OncologicalTest test){
        return oncologicalTestRepo.save(test);
    }

    public List<OncologicalTest> findAllTestsByPatientOwner(Patient owner){
        return oncologicalTestRepo.findByPatientOwner(owner);
    };
}
