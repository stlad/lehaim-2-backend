package ru.vaganov.ResourceServer.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.dto.OncologicalTestDTO;

import java.util.List;


@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface OncologicalTestMapper {
    public abstract OncologicalTest fromDto(OncologicalTestDTO dto);
    public abstract  OncologicalTestDTO toDto(OncologicalTest entity);
    public abstract List<OncologicalTestDTO> toDto(List<OncologicalTest> entity);
}
