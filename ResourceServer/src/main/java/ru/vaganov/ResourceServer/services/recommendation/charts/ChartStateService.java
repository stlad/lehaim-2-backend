package ru.vaganov.ResourceServer.services.recommendation.charts;

import lombok.RequiredArgsConstructor;
import ru.vaganov.ResourceServer.dictionary.ChartType;
import ru.vaganov.ResourceServer.dictionary.recommendation.ParameterChartAxis;
import ru.vaganov.ResourceServer.exceptions.ChartStateException;
import ru.vaganov.ResourceServer.exceptions.ParameterNotFoundException;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;
import ru.vaganov.ResourceServer.models.Patient;
import ru.vaganov.ResourceServer.models.recommendations.Recommendation;
import ru.vaganov.ResourceServer.repositories.CatalogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class ChartStateService {
    protected final CatalogRepository catalogRepository;

    public  abstract ChartType getChart();

    /**
     * Получить рекомендацию на основе входных параметров обследования
     *
     * @param patient Пациент
     * @param results Список парметров из обследования
     * @return рекомендация, соответсвующая состоянию на основе входных параметров
     */
    public abstract Recommendation getRecommendation(Patient patient, List<ParameterResult> results);

    /**
     * Создать новую рекомендацию на основе текщего обследования и типа графика
     *
     * @param recommendation данные для рекомендации ОБЯЗАТЕЛЬНО УКАЗАТЬ chartType
     * @param patient        Пациент
     * @param results        Список парметров из обследования
     * @return рекомендация, соответсвующая состоянию на основе входных параметров
     */
    public  abstract Recommendation saveRecommendation(Recommendation recommendation, Patient patient,
                                                       List<ParameterResult> results);

    protected void validateState(List<String> validationErrors) {
        if (!validationErrors.isEmpty())
            throw new ChartStateException(
                    validationErrors.stream().collect(Collectors.joining(";\n", "", "."))
            );
    }

    /** Получить значение параметра по его id из списка параметров обследования. Если невозмоэно - записать в список
     * ошибок
     * @param parameterId ИД параметра, значение которого ищем
     * @param results список результатов в обследовании
     * @param validationErrors список ошибок
     * @return значение параметра
     */
    protected Double getParamResult(Long parameterId, List<ParameterResult> results,
                                                List<String> validationErrors) {
        Optional<ParameterResult> resOpt = results.stream()
                .filter(res -> res.getParameter().getId().equals(parameterId)).findAny();

        if (resOpt.isPresent()) {
            return resOpt.get().getValue();
        }
        Parameter param = catalogRepository.findById(parameterId)
                .orElseThrow(() -> new ParameterNotFoundException(parameterId));
        validationErrors.add("Обследование не содержит результата для параметра: " + param.toString());
        return null;
    }

    /** Получить значение-отношение двух параметров. Если невозможно - записать в список ошибок
     * @param axis ось графика, отношеня параметров которого вычисляется
     * @param results список результатов в обследовании
     * @param validationErrors список ошибок
     * @return значние-отношения параметров для оси
     */
    protected Double getDivisionForChartAxis(ParameterChartAxis axis, List<ParameterResult> results,
                                             List<String> validationErrors) {
        Double firstParam = getParamResult(axis.getFirstParamId(), results, validationErrors);
        Double secondParam = getParamResult(axis.getSecondParamId(), results, validationErrors);
        if (firstParam == null || secondParam == null || secondParam == 0) {
            if (secondParam != null && secondParam == 0) {
                Parameter param = catalogRepository.findById(axis.getSecondParamId())
                        .orElseThrow(() -> new ParameterNotFoundException(axis.getSecondParamId()));
                validationErrors.add("Значение параметра " + param.toString() + " не должно быть 0");
            }
            return 0d;
        }
        return firstParam / secondParam;
    }
}
