package ru.vaganov.ResourceServer.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.exceptions.ParameterNotFoundException;
import ru.vaganov.ResourceServer.mappers.ParameterMapper;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.dto.ParameterDTO;
import ru.vaganov.ResourceServer.repositories.CatalogRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final ParameterMapper parameterMapper;

    public Parameter save(Parameter param) {
        Parameter savedParam = null;
        try {
            savedParam = catalogRepository.save(param);
            log.debug("param" + param.toString() + "saved");
        } catch (DataIntegrityViolationException e) {
            savedParam = catalogRepository.findByNameAndAdditionalName(param.getName(), param.getAdditionalName());
            log.debug("param" + param.toString() + " already exist");
        }
        return savedParam;
    }

    public void delete(Parameter parameter) {
        catalogRepository.delete(parameter);
    }

    public List<Parameter> findByResearchType(Parameter.ResearchType type) {
        return catalogRepository.findByResearchTypeOrderById(type);
    }

    public List<Parameter> findAll() {
        return catalogRepository.findAllActive();
    }

    public Parameter findById(Long id) {
        return catalogRepository.findById(id).orElse(null);
    }

    public ParameterDTO getDtoById(Long id) {
        Parameter parameter = catalogRepository.findById(id).
                orElseThrow(() -> new ParameterNotFoundException(id));
        return parameterMapper.toDto(parameter);
    }
}
