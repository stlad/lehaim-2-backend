package ru.vaganov.lehaim.catalog;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.exceptions.ParameterNotFoundException;
import ru.vaganov.lehaim.catalog.mapper.ParameterMapper;
import ru.vaganov.lehaim.catalog.entity.Parameter;
import ru.vaganov.lehaim.catalog.dto.ParameterDTO;
import ru.vaganov.lehaim.catalog.repository.ParameterCatalogRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParameterCatalogService {
    private final ParameterCatalogRepository parameterCatalogRepository;
    private final ParameterMapper parameterMapper;

    public Parameter save(Parameter param) {
        Parameter savedParam = null;
        try {
            savedParam = parameterCatalogRepository.save(param);
            log.debug("param" + param.toString() + "saved");
        } catch (DataIntegrityViolationException e) {
            savedParam = parameterCatalogRepository.findByNameAndAdditionalName(param.getName(), param.getAdditionalName());
            log.debug("param" + param.toString() + " already exist");
        }
        return savedParam;
    }

    public void delete(Parameter parameter) {
        parameterCatalogRepository.delete(parameter);
    }

    public List<Parameter> findByResearchType(Parameter.ResearchType type) {
        return parameterCatalogRepository.findByResearchTypeOrderById(type);
    }

    public List<Parameter> findAll() {
        return parameterCatalogRepository.findAllActive();
    }

    public Parameter findById(Long id) {
        return parameterCatalogRepository.findById(id).orElse(null);
    }

    public ParameterDTO getDtoById(Long id) {
        Parameter parameter = parameterCatalogRepository.findById(id).
                orElseThrow(() -> new ParameterNotFoundException(id));
        return parameterMapper.toDto(parameter);
    }
}
