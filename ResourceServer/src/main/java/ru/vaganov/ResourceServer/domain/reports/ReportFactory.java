package ru.vaganov.ResourceServer.domain.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.services.OncologicalServiceDeprecated;
import ru.vaganov.ResourceServer.services.PatientService;

import java.util.List;

@Component
public class ReportFactory {

    @Autowired
    private OncologicalServiceDeprecated oncologicalServiceDeprecated;

    @Autowired
    private PatientService patientService;

    public ReportData CreateReportDataByTest(OncologicalTest test){
        Patient patient= test.getPatientOwner();
        List<ParameterResult> resultList = oncologicalServiceDeprecated.findResultsByTest(test);
        ReportData data = new ReportData(patient, test, resultList);

        List<OncologicalTest> allTests = oncologicalServiceDeprecated.findAllTestsByPatientOwner(patient);
        for(OncologicalTest itertest: allTests){
            if(itertest.getTestDate().isBefore(test.getTestDate())){
                List<ParameterResult> resultsOfIterTest = oncologicalServiceDeprecated.findResultsByTest(itertest);
                data.addNextTestResults(resultsOfIterTest);
            }
        }
        return data;
    }



}
