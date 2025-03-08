package ru.vaganov.lehaim.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vaganov.lehaim.BaseContextTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ChartAnalyzerTest extends BaseContextTest {

    @Test
    void isTestDuringRadiationTherapy_betweenDates() {
        var patient = testData.patient().withBirthday(LocalDate.parse("1970-01-01"))
                .withTherapy(LocalDate.parse("2020-01-01"), LocalDate.parse("2022-01-01"))
                .buildAndSave();

        var test = testData.oncologicalTest().withDate(LocalDate.parse("2021-01-01"))
                .withPatient(patient).buildAndSave();

        Assertions.assertTrue(ChartAnalyzer.isTestDuringRadiationTherapy(test));
    }

    @Test
    void isTestDuringRadiationTherapy_withoutEndDate() {
        var patient = testData.patient().withBirthday(LocalDate.parse("1970-01-01"))
                .withTherapy(LocalDate.parse("2020-01-01"), null)
                .buildAndSave();

        var test = testData.oncologicalTest().withDate(LocalDate.parse("2021-01-01"))
                .withPatient(patient).buildAndSave();

        Assertions.assertTrue(ChartAnalyzer.isTestDuringRadiationTherapy(test));
    }

    @Test
    void isTestDuringRadiationTherapy_outOfBorders() {
        var patient = testData.patient().withBirthday(LocalDate.parse("1970-01-01"))
                .withTherapy(LocalDate.parse("2020-01-01"), LocalDate.parse("2022-01-01"))
                .buildAndSave();

        var test = testData.oncologicalTest().withDate(LocalDate.parse("2019-01-01"))
                .withPatient(patient).buildAndSave();

        Assertions.assertFalse(ChartAnalyzer.isTestDuringRadiationTherapy(test));
    }
}