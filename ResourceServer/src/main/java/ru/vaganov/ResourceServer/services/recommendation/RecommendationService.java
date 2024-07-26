package ru.vaganov.ResourceServer.services.recommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.exceptions.PatientNotFoundException;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepository;
import ru.vaganov.ResourceServer.repositories.PatientRepository;
import ru.vaganov.ResourceServer.services.recommendation.charts.ChartStateService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationService {
    private final PatientRepository patientRepository;
    private final ParameterResultRepository resultRepository;
    private Map<ChartType, ChartStateService> chartServices;

    public RecommendationService(PatientRepository patientRepository,
                                 ParameterResultRepository resultRepository,
                                 List<ChartStateService> chartStateServiceList) {
        this.patientRepository = patientRepository;
        this.resultRepository = resultRepository;
        chartServices = chartStateServiceList.stream().collect(Collectors.toMap(
                ChartStateService::getChart,
                service -> service
        ));
    }

    void process(UUID patientId, Long testId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));

        List<ParameterResult> results = resultRepository.findByAttachedTest_Id(testId);

        for(ChartType key : chartServices.keySet()){
            chartServices.get(key).process(patient, results);
        }
    }
}
