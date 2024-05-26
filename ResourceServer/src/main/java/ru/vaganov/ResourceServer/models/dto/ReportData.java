package ru.vaganov.ResourceServer.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vaganov.ResourceServer.enums.TestSeason;

import java.time.LocalDate;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReportData {
    private PatientDTO patient;
    private Long testId;
    private LocalDate currentTestDate;
    private List<ParameterResultRestDTO> currentResults;
    private List<ParameterResultRestDTO> previousResults;
    private TestSeason season;
}
