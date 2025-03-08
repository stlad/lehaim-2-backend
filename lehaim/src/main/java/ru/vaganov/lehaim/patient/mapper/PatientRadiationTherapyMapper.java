package ru.vaganov.lehaim.patient.mapper;

import org.mapstruct.*;
import ru.vaganov.lehaim.patient.dto.PatientRadiationTherapyDTO;
import ru.vaganov.lehaim.utils.MapperUtils;
import ru.vaganov.lehaim.patient.entity.PatientRadiationTherapy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Mapper(componentModel = "spring")
public abstract class PatientRadiationTherapyMapper {

    abstract PatientRadiationTherapyDTO toDTO(PatientRadiationTherapy entity);

    @Mapping(target = "startTherapy", source = "startTherapy", qualifiedByName = "parseDate")
    @Mapping(target = "endTherapy", source = "endTherapy", qualifiedByName = "parseDate")
    abstract PatientRadiationTherapy fromDTO(PatientRadiationTherapyDTO dto);

    /**
     * Только для создания сущности PatientRadiationTherapy.
     * <ul>
     *     <li>Если дата = null || дата = "" - установить null</li>
     *     <li>Если дата заполнена - установить значение</li>
     * </ul>
     *
     * @param date
     * @return
     */
    @Named("parseDate")
    protected LocalDate parseDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @BeforeMapping
    protected void beforeMapping(PatientRadiationTherapyDTO dto, @MappingTarget PatientRadiationTherapy entity) {
        MapperUtils.updateDateField(entity::setStartTherapy, dto.getStartTherapy());
        MapperUtils.updateDateField(entity::setEndTherapy, dto.getEndTherapy());
    }

    @Mapping(target = "startTherapy", ignore = true)
    @Mapping(target = "endTherapy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void updateFromDto(PatientRadiationTherapyDTO dto, @MappingTarget PatientRadiationTherapy entity);
}

