package ru.vaganov.lehaim.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dto.ParameterResultDTO;
import ru.vaganov.lehaim.mappers.ParameterMapper;
import ru.vaganov.lehaim.models.OncologicalTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ReportAverageCalculator {

    private final ReportMapper reportMapper;

    /**
     * Расчет среднего значения для каждого параметра из списка значений
     */
    public List<ParameterResultDTO> getCalculatedAverages(List<OncologicalTest> tests) {
        if (tests == null) {
            return null;
        }
        Map<Long, List<Double>> aggregates = new HashMap<>();

        tests.forEach(test -> {
            test.getResults().forEach(r -> {
                if (!aggregates.containsKey(r.getCatalogId())) {
                    aggregates.put(r.getCatalogId(), new ArrayList<>());
                }
                aggregates.get(r.getCatalogId()).add(r.getValue());
            });
        });

        var averages = calculateAverages(aggregates);
        return reportMapper.toReportDto(averages);
    }

    private Map<Long, Double> calculateAverages(Map<Long, List<Double>> aggregates) {
        return aggregates.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry ->
                        entry.getValue().stream().collect(Collectors.averagingDouble(Double::doubleValue))
        ));
    }

}
