package ru.vaganov.ResourceServer.services.recommendation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.dto.recommendation.RecommendationDTO;
import ru.vaganov.ResourceServer.exceptions.OncologicalTestExistsException;
import ru.vaganov.ResourceServer.exceptions.OncologicalTestNotFoundException;
import ru.vaganov.ResourceServer.exceptions.PatientNotFoundException;
import ru.vaganov.ResourceServer.exceptions.RecommendationNotFoundException;
import ru.vaganov.ResourceServer.mappers.RecommendationMapper;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;
import ru.vaganov.ResourceServer.repositories.OncologicalTestRepository;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepository;
import ru.vaganov.ResourceServer.repositories.PatientRepository;
import ru.vaganov.ResourceServer.repositories.RecommendationRepository;
import ru.vaganov.ResourceServer.services.recommendation.charts.ChartStateService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationService {
    private final PatientRepository patientRepository;
    private final ParameterResultRepository resultRepository;
    private final RecommendationMapper recommendationMapper;
    private final RecommendationRepository recommendationRepository;
    private final OncologicalTestRepository oncologicalTestRepository;
    private Map<ChartType, ChartStateService> chartServices;

    public RecommendationService(PatientRepository patientRepository,
                                 ParameterResultRepository resultRepository,
                                 RecommendationMapper recommendationMapper,
                                 RecommendationRepository recommendationRepository,
                                 OncologicalTestRepository oncologicalTestRepository,
                                 List<ChartStateService> chartStateServiceList) {
        this.patientRepository = patientRepository;
        this.resultRepository = resultRepository;
        this.recommendationMapper = recommendationMapper;
        this.recommendationRepository = recommendationRepository;
        this.oncologicalTestRepository = oncologicalTestRepository;
        chartServices = chartStateServiceList.stream().collect(Collectors.toMap(
                ChartStateService::getChart,
                service -> service
        ));
    }

    public HashMap<ChartType, RecommendationDTO> getRecommendation(Long testId) {
        HashMap<ChartType, RecommendationDTO> dto = new HashMap<>();

        List<ParameterResult> results = resultRepository.findByAttachedTest_Id(testId);
        Patient patient = getPatientByTestId(testId);
        for(ChartType key : chartServices.keySet()){
            Recommendation rec  = chartServices.get(key).getRecommendation(patient, results);
            dto.put(key, recommendationMapper.toDTO(rec));
        }
        return dto;
    }

    public RecommendationDTO getRecommendationById(UUID recommendationId){
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(()-> new RecommendationNotFoundException(recommendationId));
        return recommendationMapper.toDTO(recommendation);
    }

    public RecommendationDTO saveNewRecommendation(Long testId, RecommendationDTO dto){
        Recommendation recommendation = recommendationMapper.fromDTO(dto);
        recommendation.setId(null);
        recommendation.setDateCreated(LocalDateTime.now());

        Patient patient = getPatientByTestId(testId);
        List<ParameterResult> results = resultRepository.findByAttachedTest_Id(testId);

        recommendation = chartServices.get(recommendation.getChartType())
                .saveRecommendation(recommendation, patient, results);
        return recommendationMapper.toDTO(recommendation);
    }

    public RecommendationDTO editRecommendation(UUID recommendationId, RecommendationDTO dto){
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(()->new RecommendationNotFoundException(recommendationId));
        recommendationMapper.updateFromDto(dto, recommendation);
        recommendation.setDateUpdated(LocalDateTime.now());
        recommendation = recommendationRepository.save(recommendation);
        return recommendationMapper.toDTO(recommendation);
    }

    private Patient getPatientByTestId(Long testId){
        OncologicalTest test = oncologicalTestRepository.findById(testId)
                .orElseThrow(()-> new OncologicalTestNotFoundException(testId));
        Patient patient = test.getPatientOwner();
        if(patient == null)
            throw new PatientNotFoundException("связанный с тестом с id: "+testId);
        return patient;
    }
}
