package ru.vaganov.ResourceServer.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.mappers.ParameterResultMapper;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.dto.ParameterResultDTO;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepo;

import java.util.List;

@Service @Slf4j
public class ParameterResultsService {

    @Autowired
    private ParameterResultRepo resultRepo;

    @Autowired
    private ParameterResultMapper resultMapper;

    public List<ParameterResultDTO> getResultsByTestId(Long testId){
        return resultMapper.toDto(resultRepo.findByAttachedTest_Id(testId));
    }
}
