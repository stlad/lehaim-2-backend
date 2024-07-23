package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.ResourceServer.exceptions.DiagnosisNotFoundException;
import ru.vaganov.ResourceServer.exceptions.ParameterNotFoundException;
import ru.vaganov.ResourceServer.models.Recommendation;
import ru.vaganov.ResourceServer.models.dto.RecommendationDTO;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;
import ru.vaganov.ResourceServer.repositories.DiagnosisRepo;

@Mapper(componentModel = "spring", uses = {ParameterMapper.class})
public abstract class RecommendationMapper {

    @Autowired
    protected CatalogRepo catalogRepository;
    @Autowired
    protected DiagnosisRepo diagnosisRepository;

    @Mapping(target = "firstParamId", source = "entity.firstParam.id")
    @Mapping(target = "secondParamId", source = "entity.secondParam.id")
    @Mapping(target = "diagnosisId", source = "entity.diagnosis.id")
    public abstract RecommendationDTO toDTO(Recommendation entity);

    public Recommendation fromDTO(RecommendationDTO dto) {
        return Recommendation.builder()
                .id(dto.getId())
                .recommendation(dto.getRecommendation())
                .name(dto.getName())
                .conclusion(dto.getConclusion())
                .dateCreated(dto.getDateCreated())
                .dateUpdated(dto.getDateUpdated())
                .rangeMax(dto.getRangeMax())
                .rangeMin(dto.getRangeMin())
                .firstParam(catalogRepository.findById(dto.getFirstParamId())
                        .orElseThrow(() -> new ParameterNotFoundException(dto.getFirstParamId())))
                .secondParam(catalogRepository.findById(dto.getSecondParamId())
                        .orElseThrow(() -> new ParameterNotFoundException(dto.getSecondParamId())))
                .diagnosis(diagnosisRepository.findById(dto.getDiagnosisId())
                        .orElseThrow(() -> new DiagnosisNotFoundException(dto.getDiagnosisId())))
                .build();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    protected abstract void updateSimpleFieldsFromDto(RecommendationDTO dto, @MappingTarget Recommendation entity);


    public void updateFromDto(RecommendationDTO dto, @MappingTarget Recommendation entity){
        updateSimpleFieldsFromDto(dto, entity);
        if(dto.getFirstParamId() != null){
            entity.setFirstParam(catalogRepository.findById(dto.getFirstParamId())
                    .orElseThrow(() -> new ParameterNotFoundException(dto.getFirstParamId())));
        }
        if(dto.getSecondParamId() != null){
            entity.setSecondParam(catalogRepository.findById(dto.getSecondParamId())
                    .orElseThrow(() -> new ParameterNotFoundException(dto.getFirstParamId())));
        }
        if(dto.getDiagnosisId() != null){
            entity.setDiagnosis(diagnosisRepository.findById(dto.getDiagnosisId())
                    .orElseThrow(() -> new DiagnosisNotFoundException(dto.getDiagnosisId())));
        }
    };
}
