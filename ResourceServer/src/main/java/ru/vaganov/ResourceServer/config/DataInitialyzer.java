package ru.vaganov.ResourceServer.config;

import org.hibernate.loader.ast.spi.Loadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.services.CatalogService;
import ru.vaganov.ResourceServer.services.OncologicalService;
import ru.vaganov.ResourceServer.services.PatientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitialyzer {

    @Autowired private CatalogService catalogService;
    @Autowired private PatientService patientService;
    @Autowired private OncologicalService oncologicalService;

    public void loadTestPatient(){
        Patient p = Patient.builder().name("Иван").lastname("Иванов").patronymic("Иванович")
                .gender(Patient.Gender.Male).birthdate(LocalDate.of(1980,10,1))
                .alive(true).mainDiagnosis("C50").build();
        patientService.save(p);

        Patient p1 = Patient.builder().name("Ирина").lastname("Бупкина").patronymic("Андреевна")
                .gender(Patient.Gender.Female).birthdate(LocalDate.of(1980,10,1))
                .alive(false).deathdate(LocalDate.of(1950,10,5))
                .mainDiagnosis("C50").build();
        patientService.save(p1);

        OncologicalTest test1 = OncologicalTest.builder().testDate(LocalDate.of(2000,1,2)).patientOwner(p).build();
        OncologicalTest test2 = OncologicalTest.builder().testDate(LocalDate.of(2000,1,2)).patientOwner(p).build();
        oncologicalService.saveTest(test1);
        oncologicalService.saveTest(test2);


        ParameterResult res1 = ParameterResult.builder()
                .attachedTest(test1)
                .parameter(catalogService.findById(1L))
                .value(1.)
                .build();
        ParameterResult res2 = ParameterResult.builder()
                .attachedTest(test2)
                .parameter(catalogService.findById(2L))
                .value(2.)
                .build();
        oncologicalService.saveResult(res1);
        oncologicalService.saveResult(res2);
    }

    public static List<Patient> createListOfPatients(){
        List<Patient> result = new ArrayList<>();

        Patient p = Patient.builder().name("ИВАН").lastname("ИВАНОВ").patronymic("ИВАНОВИЧ")
                .gender(Patient.Gender.Male).birthdate(LocalDate.of(1990,10,1))
                .alive(true).mainDiagnosis("C50").build();
        result.add(p);

        p = Patient.builder().name("Андрей").lastname("Андреев").patronymic("Андреевич")
                .gender(Patient.Gender.Male).birthdate(LocalDate.of(1950,1,25))
                .alive(false).deathdate(LocalDate.of(2010,5,4)).mainDiagnosis("C50").build();


        return result;
    }


}
