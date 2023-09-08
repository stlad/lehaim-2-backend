package ru.vaganov.ResourceServer.config;

import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataInitialyzer {

    public static List<Patient> createListOfPatients(){
        List<Patient> result = new ArrayList<>();

        Patient p = Patient.builder().name("ИВАН").lastname("ИВАНОВ").patronymic("ИВАНОВИЧ")
                .gender(Patient.Gender.Male).birthdate(LocalDate.of(1990,10,1))
                .alive(true).mainDiagnosis("C50").build();
        result.add(p);

        p = Patient.builder().name("Андрей").lastname("Андреев").patronymic("Андреевич")
                .gender(Patient.Gender.Male).birthdate(LocalDate.of(1950,1,25))
                .alive(false).deathdate(LocalDate.of(2010,5,4)).mainDiagnosis("C50").build();

        result.add(p);

        return result;
    }


}
