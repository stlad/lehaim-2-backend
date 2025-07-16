package ru.vaganov.lehaim.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.lehaim.report.dto.ReportData;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReportServiceNew {

    private final OncologicalTestRepository oncologicalTestRepository;

    public ReportData createReportByTestId(UUID patientId, Long testId) {
        var test = oncologicalTestRepository.findById(testId).orElseThrow();


    }
}


