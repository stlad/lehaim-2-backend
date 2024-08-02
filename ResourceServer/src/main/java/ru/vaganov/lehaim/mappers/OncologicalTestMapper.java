package ru.vaganov.lehaim.mappers;

import org.mapstruct.Mapper;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.dto.OncologicalTestDTO;

import java.util.List;


@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface OncologicalTestMapper {
    public abstract OncologicalTest fromDto(OncologicalTestDTO dto);
    public abstract  OncologicalTestDTO toDto(OncologicalTest entity);
    public abstract List<OncologicalTestDTO> toDto(List<OncologicalTest> entity);
}
