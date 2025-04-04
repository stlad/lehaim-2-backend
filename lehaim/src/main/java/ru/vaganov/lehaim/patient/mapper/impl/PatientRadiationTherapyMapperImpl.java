package ru.vaganov.lehaim.patient.mapper.impl;

import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.patient.dto.PatientRadiationTherapyDTO;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;
import ru.vaganov.lehaim.patient.mapper.PatientRadiationTherapyMapper;

import static ru.vaganov.lehaim.utils.MapperUtils.set;
import static ru.vaganov.lehaim.utils.MapperUtils.update;

@Component
public class PatientRadiationTherapyMapperImpl implements PatientRadiationTherapyMapper {

    @Override
    public PatientRadiationTherapyDTO toDTO(PatientRadiationTherapy therapy) {
        if (therapy == null) {
            return null;
        }

        var dto = PatientRadiationTherapyDTO.builder();

        set(dto::startTherapy, therapy.getStartTherapy());
        set(dto::endTherapy, therapy.getEndTherapy());
        set(dto::comment, therapy.getComment());

        return dto.build();
    }

    @Override
    public PatientRadiationTherapy fromDTO(PatientRadiationTherapyDTO dto) {
        if (dto == null) {
            return null;
        }

        var therapy = PatientRadiationTherapy.builder();

        set(therapy::startTherapy, dto.getStartTherapy());
        set(therapy::endTherapy, dto.getEndTherapy());
        set(therapy::comment, dto.getComment());

        return therapy.build();
    }

    @Override
    public void updateFromDto(PatientRadiationTherapyDTO dto, PatientRadiationTherapy entity) {

        update(entity::setStartTherapy, dto.getStartTherapy());
        update(entity::setEndTherapy, dto.getEndTherapy());
        update(entity::setComment, dto.getComment());

    }
}
