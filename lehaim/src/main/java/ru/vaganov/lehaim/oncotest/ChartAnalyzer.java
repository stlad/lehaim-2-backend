package ru.vaganov.lehaim.oncotest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.oncotest.entity.OncologicalTest;
import ru.vaganov.lehaim.oncotest.entity.ParameterResult;

import java.util.*;

import static ru.vaganov.lehaim.dictionary.MostUsedParameters.*;

@Service
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChartAnalyzer {

    public static Boolean isTestDuringRadiationTherapy(OncologicalTest test){
        var patient = test.getPatientOwner();
        if(patient.getRadiationTherapy() == null){
            return false;
        }
        return patient.getRadiationTherapy().isDateInTherapy(test.getTestDate());
    }

    /**
     * Проверка возможности нарисовать график на основе текущих указаных параметров обследования
     *
     * @param results ПАраметры обследования
     * @return Список графиков, которые возможно отообразить
     */
    public static List<ChartType> getPossibleChartsByTEstId(List<ParameterResult> results) {
        List<ChartType> possibleCharts = new ArrayList<>();
        for (ChartType chart : ChartType.values()) {
            boolean canBeProcessed = switch (chart) {
                case T_CELL ->  checkResult(results, NEU, LYMF, CD3, CD4, CD8);
                case B_CELL ->  checkResult(results, NEU, CD19, LYMF, CD4, IgG, IgA, IgM, TNK_CELLS);
                case CYTOKINE_PAIRS -> checkResult(results, TNFa_STIM, TNFa_SPON, IFNy_SPON, IFFy_STIM, IL2_SPON, IL2_STIM);
                case REGENERATION -> checkResult(results, NEU, MON, LYMF, PLT, WBC, HGB);
                case SYSTEMIC_INFLAMMATION -> checkResult(results, NEU, LYMF, MON, PLT);
            };
            if (canBeProcessed) {
                possibleCharts.add(chart);
            }
        }
        return possibleCharts;
    }

    private static Boolean checkResult(List<ParameterResult> results, MostUsedParameters... params) {
        return Arrays.stream(params).allMatch(param -> {
            Double value = getValue(results, param.getId());
            return value != null && !value.equals(0.);
        });
    }

    private static Double getValue(List<ParameterResult> results, Long id) {
        return results.stream().filter(res -> res.getParameter().getId().equals(id)).findAny()
                .map(ParameterResult::getValue).orElse(null);
    }
}
