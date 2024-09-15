package ru.vaganov.lehaim.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vaganov.lehaim.dictionary.ChartType;
import ru.vaganov.lehaim.dictionary.recommendation.RegenerationParameterRanges;
import ru.vaganov.lehaim.models.Parameter;
import ru.vaganov.lehaim.models.ParameterResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChartAnalyzer {
    private static final Long NEU = 4L;
    private static final Long LYMF = 2L;
    private static final Long MON = 3L;
    private static final Long PLT = 8L;
    private static final Long CD19 = 18L;
    private static final Long CD8 =20L;
    private static final Long CD4 =19L;
    private static final Long CD3 =17L;
    private static final Long IFFy_STIM =36L;
    private static final Long IFNy_SPON =37L;
    private static final Long TNFa_STIM =38L;
    private static final Long TNFa_SPON =39L;
    private static final Long IL2_STIM =40L;
    private static final Long IL2_SPON =41L;

    /**
     * Проверка возможности нарисовать график на основе текущих указаных параметров обследования
     * @param results ПАраметры обследования
     * @return Список графиков, которые возможно отообразить
     */
    public static List<ChartType> getPossibleChartsByTEstId(List<ParameterResult> results){
        Set<Long> ids = results.stream().map(ParameterResult::getParameter)
                .map(Parameter::getId).collect(Collectors.toSet());
        List<ChartType> possibleCharts = new ArrayList<>();
        for(ChartType chart: ChartType.values()){
            boolean canBeProcessed = switch (chart){
                case T_CELL -> checkRes(ids, NEU, LYMF, CD3, CD4, CD8);
                case B_CELL -> checkRes(ids, NEU, CD19, LYMF, CD8, CD4);
                case CYTOKINE_PAIRS -> checkRes(ids,TNFa_STIM, TNFa_SPON, IFNy_SPON, IFFy_STIM, IL2_SPON, IL2_STIM );
                case REGENERATION -> checkRes(ids, NEU, MON, LYMF);
                case SYSTEMIC_INFLAMMATION -> checkRes(ids, NEU, LYMF, MON, PLT);
            };
            if(canBeProcessed){
                possibleCharts.add(chart);
            }
        }
        return possibleCharts;
    }

    protected static Boolean checkRes(Set<Long> actualIds, Long... expectedIds) {
        return actualIds.containsAll(Arrays.stream(expectedIds).collect(Collectors.toSet()));
    }
}
