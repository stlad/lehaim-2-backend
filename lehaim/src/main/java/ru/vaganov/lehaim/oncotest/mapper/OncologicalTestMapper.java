package ru.vaganov.lehaim.oncotest.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.oncotest.dto.OncologicalTestDTO;
import ru.vaganov.lehaim.oncotest.dto.OncologicalTestRestDTO;
import ru.vaganov.lehaim.oncotest.entity.OncologicalTest;
import ru.vaganov.lehaim.oncotest.entity.ParameterResult;
import ru.vaganov.lehaim.patient.mapper.PatientMapper;
import ru.vaganov.lehaim.oncotest.repository.ParameterResultRepository;
import ru.vaganov.lehaim.oncotest.ChartAnalyzer;

import java.time.LocalDate;
import java.util.List;


@Mapper(componentModel = "spring", uses = {PatientMapper.class, ParameterResultMapper.class})
public abstract class OncologicalTestMapper {
    @Autowired
    protected ParameterResultMapper parameterResultMapper;
    @Autowired
    protected ParameterResultRepository parameterResultRepository;
    @Autowired
    protected EntityManager entityManager;

    abstract OncologicalTest fromDto(OncologicalTestDTO dto);

    public OncologicalTestDTO toDto(OncologicalTest entity) {
        List<ChartType> possibleCharts = ChartAnalyzer
                .getPossibleChartsByTEstId(parameterResultRepository.findByAttachedTest_Id(entity.getId()));
        return OncologicalTestDTO.builder()
                .id(entity.getId())
                .testNote(entity.getTestNote())
                .testDate(entity.getTestDate())
                .possibleCharts(possibleCharts)
                .isDuringRadiationTherapy(ChartAnalyzer.isTestDuringRadiationTherapy(entity))
                .build();
    }

    public abstract List<OncologicalTestDTO> toDto(List<OncologicalTest> entity);

    public OncologicalTestRestDTO toRestDto(Long testId, LocalDate testDate, String testNote,
                                            List<ParameterResult> parameterResults) {
        var test = entityManager.getReference(OncologicalTest.class, testId);

        return OncologicalTestRestDTO.builder()
                .id(testId)
                .testDate(testDate)
                .results(parameterResultMapper.toRestDto(parameterResults))
                .possibleCharts(ChartAnalyzer.getPossibleChartsByTEstId(parameterResults))
                .isDuringRadiationTherapy(ChartAnalyzer.isTestDuringRadiationTherapy(test))
                .testNote(testNote)
                .build();
    };
}
