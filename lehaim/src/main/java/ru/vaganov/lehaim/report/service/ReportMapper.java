package ru.vaganov.lehaim.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.oncotest.dto.ParameterResultDTO;
import ru.vaganov.lehaim.catalog.mapper.ParameterMapper;
import ru.vaganov.lehaim.oncotest.entity.ParameterResult;
import ru.vaganov.lehaim.catalog.repository.ParameterCatalogRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReportMapper {
    private final ParameterMapper parameterMapper;
    private final ParameterCatalogRepository parameterCatalogRepository;

    public ParameterResultDTO toReportDto(Long catalogId, Double value) {
        var parameter = parameterCatalogRepository.findById(catalogId).orElseThrow();
        return ParameterResultDTO.builder()
                .parameter(parameterMapper.toDto(parameter))
                .value(BigDecimal.valueOf(value).setScale(2, RoundingMode.DOWN).doubleValue())
                .build();
    }

    public ParameterResultDTO toReportDto(ParameterResult result) {
        var parameter = result.getParameter();
        return ParameterResultDTO.builder()
                .parameter(parameterMapper.toDto(parameter))
                .value(BigDecimal.valueOf(result.getValue()).setScale(2, RoundingMode.DOWN).doubleValue())
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
