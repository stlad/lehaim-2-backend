package ru.vaganov.lehaim.exceptions;

import java.util.UUID;

public class GeneNotFoundException extends LehaimException{
    private final static String baseMessage = "Не найден ген: ";

    public GeneNotFoundException(Long id){
        super(baseMessage+id);
    }
    public GeneNotFoundException(Integer diagnosisId, Long geneId){
        super(String.format("Для диагноза %s не может быть гена %s", diagnosisId, geneId));
    }

    public GeneNotFoundException(UUID patientId, Integer diagnosisId, Long geneId) {
        super(String.format("Значение диагноза-гена %s-%s у пациета %s отсутствует", diagnosisId, geneId, patientId));
    }

    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
