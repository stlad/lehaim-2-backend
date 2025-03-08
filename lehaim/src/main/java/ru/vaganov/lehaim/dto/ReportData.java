package ru.vaganov.lehaim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.lehaim.dictionary.TestSeason;
import ru.vaganov.lehaim.patient.dto.PatientDTO;

import java.time.LocalDate;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReportData {
    private PatientDTO patient;
    private Long testId;
    private LocalDate currentTestDate;
    private String currentTestNote;
    private List<ParameterResultDTO> currentResults;
    private List<ParameterResultDTO> previousResults;
    private TestSeason season;
}
