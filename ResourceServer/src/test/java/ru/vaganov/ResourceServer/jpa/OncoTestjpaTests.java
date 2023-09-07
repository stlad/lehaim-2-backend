package ru.vaganov.ResourceServer.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.repositories.CatalogRepo;
import ru.vaganov.ResourceServer.repositories.OncologicalTestRepo;
import ru.vaganov.ResourceServer.repositories.ParameterResultRepo;
import ru.vaganov.ResourceServer.repositories.PatientRepo;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OncoTestjpaTests {

    @Autowired private ParameterResultRepo parameterResultRepo;
    @Autowired private OncologicalTestRepo oncologicalTestRepo;
    @Autowired private CatalogRepo catalogRepo;
    @Autowired private PatientRepo patientRepo;

    @Test
    public void deleteAllAttachedResultsWithParameter(){
        Parameter parameter = Parameter.builder().name("TEST PARAMETER").additionalName("TEST").build();
        parameter = catalogRepo.save(parameter);


        Parameter parameter1 = Parameter.builder().name("TEST PARAMETER2").additionalName("TEST2").build();
        parameter1 = catalogRepo.save(parameter1);

        ParameterResult result1 = ParameterResult.builder().parameter(parameter).value(4.).build();
        ParameterResult result2 = ParameterResult.builder().parameter(parameter).value(2.).build();
        ParameterResult result3 = ParameterResult.builder().parameter(parameter1).value(2.).build();

        parameterResultRepo.save(result1);
        parameterResultRepo.save(result2);
        parameterResultRepo.save(result3);

        catalogRepo.delete(parameter);
        Assertions.assertEquals(1, parameterResultRepo.findAll().size());
    }

    @Test
    public void deleteAllAttachedResultsWithTest(){
        OncologicalTest test1 = OncologicalTest.builder()
                .testDate(LocalDate.of(2000,10,10)).build();
        OncologicalTest test2= OncologicalTest.builder()
                .testDate(LocalDate.of(2005,1,1)).build();
        oncologicalTestRepo.save(test1);
        oncologicalTestRepo.save(test2);

        ParameterResult result1 = ParameterResult.builder().attachedTest(test1).value(4.).build();
        ParameterResult result2 = ParameterResult.builder().attachedTest(test1).value(2.).build();
        ParameterResult result3 = ParameterResult.builder().attachedTest(test2).value(2.).build();

        parameterResultRepo.save(result1);
        parameterResultRepo.save(result2);
        parameterResultRepo.save(result3);

        Assertions.assertEquals(3, parameterResultRepo.findAll().size());
        oncologicalTestRepo.delete(test1);
        Assertions.assertEquals(1, oncologicalTestRepo.findAll().size());
        Assertions.assertEquals(1, parameterResultRepo.findAll().size());
    }

    @Test
    public void deleteResultAndTestWithDeletingPatient(){
        Patient patient = Patient.builder().name("IVAN").lastname("Ivanov") .build();
        patient = patientRepo.save(patient);


        OncologicalTest test1 = OncologicalTest.builder()
                .testDate(LocalDate.of(2000,10,10))
                .patientOwner(patient).build();
        OncologicalTest test2= OncologicalTest.builder()
                .testDate(LocalDate.of(2005,1,1))
                .patientOwner(patient).build();
        oncologicalTestRepo.save(test1);
        oncologicalTestRepo.save(test2);


        ParameterResult result1 = ParameterResult.builder().attachedTest(test1).value(4.).build();
        ParameterResult result2 = ParameterResult.builder().attachedTest(test1).value(2.).build();
        ParameterResult result3 = ParameterResult.builder().attachedTest(test2).value(2.).build();

        parameterResultRepo.save(result1);
        parameterResultRepo.save(result2);
        parameterResultRepo.save(result3);

        patientRepo.delete(patient);
        Assertions.assertEquals(0, parameterResultRepo.findAll().size());
        Assertions.assertEquals(0, oncologicalTestRepo.findAll().size());

    }
}
