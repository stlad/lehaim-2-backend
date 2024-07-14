package ru.vaganov.ResourceServer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.ResourceServer.mappers.RecommendationMapper;
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

    public RecommendationDTO findById(Long id){
        return recommendationMapper.toDTO(recommendationRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("recommendation not found")
        ));
    }

    public RecommendationDTO save(RecommendationDTO dto){
        return null;
    }

}
