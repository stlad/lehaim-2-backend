package ru.vaganov.lehaim.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dto.oncotests.OncologicalTestDTO;
import ru.vaganov.lehaim.dto.oncotests.OncologicalTestRestDTO;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;
import ru.vaganov.lehaim.services.ChartAnalyzer;

import java.time.LocalDate;
import java.util.List;


@Mapper(componentModel = "spring", uses = {PatientMapper.class, ParameterResultMapper.class})
public abstract class OncologicalTestMapper {
    @Autowired
    protected ParameterResultMapper parameterResultMapper;
    @Autowired
    protected ParameterResultRepository parameterResultRepository;

    abstract OncologicalTest fromDto(OncologicalTestDTO dto);

    public OncologicalTestDTO toDto(OncologicalTest entity) {
        List<ChartType> possibleCharts = ChartAnalyzer
                .getPossibleChartsByTEstId(parameterResultRepository.findByAttachedTest_Id(entity.getId()));
        return OncologicalTestDTO.builder()
                .id(entity.getId())
                .testNote(entity.getTestNote())
                .testDate(entity.getTestDate())
                .possibleCharts(possibleCharts)
                .build();
    }

    public abstract List<OncologicalTestDTO> toDto(List<OncologicalTest> entity);

    public OncologicalTestRestDTO toRestDto(Long testId, LocalDate testDate, String testNote,
                                            List<ParameterResult> parameterResults) {
        return OncologicalTestRestDTO.builder()
                .id(testId)
                .testDate(testDate)
                .results(parameterResultMapper.toRestDto(parameterResults))
                .possibleCharts(ChartAnalyzer.getPossibleChartsByTEstId(parameterResults))
                .testNote(testNote)
                .build();
    };
}
