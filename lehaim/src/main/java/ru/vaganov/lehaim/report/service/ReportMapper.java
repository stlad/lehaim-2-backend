package ru.vaganov.lehaim.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.dto.ParameterResultDTO;
import ru.vaganov.lehaim.mappers.ParameterMapper;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.repositories.CatalogRepository;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReportMapper {
    private final ParameterMapper parameterMapper;
    private final CatalogRepository catalogRepository;

    public ParameterResultDTO toReportDto(Long catalogId, Double value) {
        var parameter = catalogRepository.findById(catalogId).orElseThrow();
        return ParameterResultDTO.builder()
                .parameter(parameterMapper.toDto(parameter))
                .value(value)
                .build();
    }

    public ParameterResultDTO toReportDto(ParameterResult result) {
        var parameter = result.getParameter();
        return ParameterResultDTO.builder()
                .parameter(parameterMapper.toDto(parameter))
                .value(result.getValue())
                .build();
    }

    public List<ParameterResultDTO> toReportDto(Map<Long, Double> results) {
        return results.entrySet().stream().map(
                entry -> toReportDto(entry.getKey(), entry.getValue())
        ).toList();
    }

    public List<ParameterResultDTO> toReportDto(List<ParameterResult> results) {
        return results.stream().map(this::toReportDto).toList();
    }
}
