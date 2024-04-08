package ru.vaganov.ResourceServer.domain.reports;


import lombok.Data;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ReportData {


    private String fullName;
    private Patient.Gender gender;
    private LocalDate testDate;
    private LocalDate birthDate;
    private Integer age;
    private String mainDiagnosis;
    private String otherDiagnosis;
    private List<ParameterResult> results;
    private CumulativeAvgTest averageOfAllResults;

    public ReportData(Patient patient, OncologicalTest test, List<ParameterResult> finalResults){
        fullName = String.format("%s %s %s", patient.getLastname(), patient.getName(), patient.getPatronymic());
        gender = patient.getGender();
        birthDate = patient.getBirthdate();
        age = test.getTestDate().getYear() - birthDate.getYear();
        //mainDiagnosis = patient.getMainDiagnosis();
        //otherDiagnosis = patient.getOtherDiagnosis();

        testDate = test.getTestDate();
        results = finalResults;

        averageOfAllResults = new CumulativeAvgTest(finalResults);
    }

    public void addNextTestResults(List<ParameterResult> results){
        averageOfAllResults.addNextTest(results);
    }

}

//@Data
//class ReportResult{
//
//    private String name;
//    private String additionalName;
//    private Double value;
//    private Double refMin;
//    private Double refMax;
//    private String unit;
//
//    public ReportResult(ParameterResult result){
//        setName(result.getParameter().getName());
//        setAdditionalName(result.getParameter().getAdditionalName());
//        setValue(result.getValue());
//        setRefMax(result.getParameter().getRefMax());
//        setRefMin(result.getParameter().getRefMin());
//        setUnit(result.getParameter().getUnit());
//    }
//}

