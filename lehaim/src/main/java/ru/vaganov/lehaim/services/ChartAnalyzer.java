package ru.vaganov.lehaim.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dto.oncotests.OncologicalTestSummaryDTO;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartAnalyzer {

    public List<ChartType> getPossibleChartsByTEstId(List<ParameterResult> results){
        return null;
    }
}
