package ru.vaganov.lehaim.services.recommendation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dto.recommendation.RecommendationDTO;
import ru.vaganov.lehaim.exceptions.*;
import ru.vaganov.lehaim.mappers.RecommendationMapper;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.models.Patient;
import ru.vaganov.lehaim.models.recommendations.Recommendation;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;
import ru.vaganov.lehaim.repositories.recommendation.RecommendationRepository;
import ru.vaganov.lehaim.services.recommendation.charts.ChartStateService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
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

    public Map<ChartType, RecommendationDTO> getRecommendation(Long testId) {
        Map<ChartType, RecommendationDTO> dto = new EnumMap<>(ChartType.class);

        List<ParameterResult> results = resultRepository.findByAttachedTest_Id(testId);
        Patient patient = getPatientByTestId(testId);
        for (ChartType key : ChartType.values()) {
            RecommendationDTO recDto;
            try {
                Recommendation rec = chartServices.containsKey(key) ?
                        chartServices.get(key).getRecommendation(patient, results) : null;
                recDto = recommendationMapper.toDTO(rec);
                if (recDto == null) {
                    recDto = RecommendationDTO.builder().chartType(key).build();
                }
            }catch (ChartStateException | NotImplementedException exception ){
                recDto = RecommendationDTO.builder().errorMessage(exception.getMessage()).chartType(key).build();
            }

            dto.put(key, recDto);
        }
        return dto;
    }

    @Deprecated(forRemoval = true)
    public RecommendationDTO getRecommendation(Long testId, ChartType type) {
        List<ParameterResult> results = resultRepository.findByAttachedTest_Id(testId);
        Patient patient = getPatientByTestId(testId);
        Recommendation rec = chartServices.containsKey(type) ?
                chartServices.get(type).getRecommendation(patient, results) : null;
        RecommendationDTO recDto = recommendationMapper.toDTO(rec);

        if (recDto == null) {
            recDto = RecommendationDTO.builder().chartType(type).build();
        }
        return recDto;
    }

    public RecommendationDTO getRecommendationById(UUID recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new RecommendationNotFoundException(recommendationId));
        return recommendationMapper.toDTO(recommendation);
    }

    public RecommendationDTO saveNewRecommendation(Long testId, RecommendationDTO dto) {
        Recommendation recommendation = recommendationMapper.fromDTO(dto);
        recommendation.setId(null);
        recommendation.setDateCreated(LocalDateTime.now());

        Patient patient = getPatientByTestId(testId);
        List<ParameterResult> results = resultRepository.findByAttachedTest_Id(testId);

        recommendation = chartServices.get(recommendation.getChartType())
                .saveRecommendation(recommendation, patient, results);
        return recommendationMapper.toDTO(recommendation);
    }

    public RecommendationDTO editRecommendation(UUID recommendationId, RecommendationDTO dto) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new RecommendationNotFoundException(recommendationId));
        recommendationMapper.updateFromDto(dto, recommendation);
        recommendation.setDateUpdated(LocalDateTime.now());
        recommendation = recommendationRepository.save(recommendation);
        return recommendationMapper.toDTO(recommendation);
    }

    private Patient getPatientByTestId(Long testId) {
        OncologicalTest test = oncologicalTestRepository.findById(testId)
                .orElseThrow(() -> new OncologicalTestNotFoundException(testId));
        Patient patient = test.getPatientOwner();
        if (patient == null)
            throw new PatientNotFoundException("связанный с тестом с id: " + testId);
        return patient;
    }
}
