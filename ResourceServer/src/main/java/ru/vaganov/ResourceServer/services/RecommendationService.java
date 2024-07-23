package ru.vaganov.ResourceServer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.exceptions.ParameterNotFoundException;
import ru.vaganov.ResourceServer.exceptions.RecommendationNotFoundException;
import ru.vaganov.ResourceServer.mappers.RecommendationMapper;
import ru.vaganov.ResourceServer.models.Recommendation;
import ru.vaganov.ResourceServer.models.dto.RecommendationDTO;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;
import ru.vaganov.ResourceServer.repositories.RecommendationRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final RecommendationRepo recommendationRepository;
    private final CatalogRepo catalogRepo;
    private final RecommendationMapper recommendationMapper;

    public RecommendationDTO findById(Long id) {
        return recommendationMapper.toDTO(recommendationRepository.findById(id).orElseThrow(
                () -> new RecommendationNotFoundException(id)
        ));
    }

    public RecommendationDTO saveRecommendation(RecommendationDTO dto) {
        Recommendation recommendation = recommendationMapper.fromDTO(dto);
        recommendation = recommendationRepository.save(recommendation);
        return recommendationMapper.toDTO(recommendation);
    }

    public RecommendationDTO editRecommendation(Long id, RecommendationDTO dto) {
        Recommendation target = recommendationRepository.findById(id)
                .orElseThrow(() -> new ParameterNotFoundException(id));
        dto.setId(null);

        recommendationMapper.updateFromDto(dto, target);
        target = recommendationRepository.save(target);
        return recommendationMapper.toDTO(target);
    }
}
