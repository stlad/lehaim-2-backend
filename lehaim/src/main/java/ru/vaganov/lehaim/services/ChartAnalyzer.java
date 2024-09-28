package ru.vaganov.lehaim.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.MostUsedParameters;
import ru.vaganov.lehaim.models.Parameter;
import ru.vaganov.lehaim.models.ParameterResult;

import java.util.*;
import java.util.stream.Collectors;

import static ru.vaganov.lehaim.dictionary.MostUsedParameters.*;

@Service
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChartAnalyzer {
    /**
     * Проверка возможности нарисовать график на основе текущих указаных параметров обследования
     *
     * @param results ПАраметры обследования
     * @return Список графиков, которые возможно отообразить
     */
    public static List<ChartType> getPossibleChartsByTEstId(List<ParameterResult> results) {
        Set<Long> ids = results.stream().map(ParameterResult::getParameter)
                .map(Parameter::getId).collect(Collectors.toSet());
        List<ChartType> possibleCharts = new ArrayList<>();
        for (ChartType chart : ChartType.values()) {
            boolean canBeProcessed = switch (chart) {
                case T_CELL -> checkContains(ids, NEU, LYMF, CD3, CD4, CD8)
                        && checkNotZero(results, LYMF, CD4, CD8, CD3);
                case B_CELL -> checkContains(ids, NEU, CD19, LYMF, CD8, CD4)
                        && checkNotZero(results, LYMF, CD4, CD8, CD19);
                case CYTOKINE_PAIRS ->
                        checkContains(ids, TNFa_STIM, TNFa_SPON, IFNy_SPON, IFFy_STIM, IL2_SPON, IL2_STIM)
                                && checkNotZero(results, TNFa_SPON, IFNy_SPON, IL2_SPON);
                case REGENERATION -> checkContains(ids, NEU, MON, LYMF)
                        && checkNotZero(results, MON, LYMF);
                case SYSTEMIC_INFLAMMATION -> checkContains(ids, NEU, LYMF, MON, PLT)
                        && checkNotZero(results, LYMF, MON);
            };
            if (canBeProcessed) {
                possibleCharts.add(chart);
            }
        }
        return possibleCharts;
    }

    private static Boolean checkContains(Set<Long> actualIds, MostUsedParameters... params) {
        return actualIds.containsAll(
                Arrays.stream(params).map(MostUsedParameters::getId)
                        .filter(val -> val > 0L)
                        .collect(Collectors.toSet()));
    }

    private static Boolean checkNotZero(List<ParameterResult> results, MostUsedParameters... params) {
        return Arrays.stream(params).allMatch(param -> getValue(results, param.getId()) != 0.);
    }

    private static Double getValue(List<ParameterResult> results, Long id) {
        Optional<ParameterResult> resOpt = results.stream()
                .filter(res -> res.getParameter().getId().equals(id)).findAny();
        if (resOpt.isPresent()) {
            return resOpt.get().getValue();
        }
        return 0.;
    }
}
