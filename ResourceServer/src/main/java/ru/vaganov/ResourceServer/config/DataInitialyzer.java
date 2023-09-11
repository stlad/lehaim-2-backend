package ru.vaganov.ResourceServer.config;

import org.hibernate.loader.ast.spi.Loadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import ru.vaganov.ResourceServer.models.OncologicalTest;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.services.CatalogService;
import ru.vaganov.ResourceServer.services.OncologicalService;
import ru.vaganov.ResourceServer.services.PatientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        createRandomOncoTestsForPatient(p, 3);



        Patient p1 = Patient.builder().name("Ирина").lastname("Бупкина").patronymic("Андреевна")
                .gender(Patient.Gender.Female).birthdate(LocalDate.of(1950,2,25))
                .alive(false).deathdate(LocalDate.of(1999,11,5))
                .mainDiagnosis("C50").build();
        patientService.save(p1);

        createRandomOncoTestsForPatient(p1, 2);
    }

    /**
     * Создание нескольких анализов на случайные даты
     * @param patient чьи анализы
     * @param testnum сколько анализов
     */
    private void createRandomOncoTestsForPatient(Patient patient, Integer testnum){
        LocalDate min = patient.getBirthdate();
        LocalDate max = patient.getDeathdate() == null ? LocalDate.now() : patient.getDeathdate();
        for(int i = 0; i<testnum;i++){
            int year = (int)(min.getYear() + Math.random() * (max.getYear() - 1 - min.getYear()));
            int month = (int)(1 + Math.random()*(12 - 1));
            int date = (int)(1 + Math.random()*(30 - 1));
            LocalDate generated = LocalDate.of(year, month, date);
            OncologicalTest test = OncologicalTest.builder()
                    .patientOwner(patient)
                    .testDate(generated)
                    .build();
            oncologicalService.saveTest(test);
            createRandomResultsForTest(test, 0.3);
        }

    }

    /**
     * Создание случайных результатов по всем параметрам из каталога
     * @param test анализ, для которого генерируются результаты
     * @param delta поправка, % 0 <= delta <= 1 для допустимых от норм значение генерации
     */
    private void createRandomResultsForTest(OncologicalTest test, double delta){
        List<Parameter> catalog = catalogService.findAll();

        for(Parameter param : catalog){
            double min = (1 - delta) * param.getRefMin();
            double max = (1 + delta) * param.getRefMax();
            double generatedValue = min + Math.random() * (max - min);

            generatedValue = Math.round(generatedValue * 100.0) / 100.0;
            ParameterResult res = ParameterResult.builder()
                    .attachedTest(test).parameter(param)
                    .value(generatedValue).build();
            oncologicalService.saveResult(res);
        }
    }
}
