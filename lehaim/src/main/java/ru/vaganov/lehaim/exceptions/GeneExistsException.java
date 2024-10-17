package ru.vaganov.lehaim.exceptions;

import java.util.UUID;

public class GeneExistsException extends LehaimException{
    public GeneExistsException(UUID patientId, Integer diagnosisId, Long geneId){
        super(String.format("Значение диагноза-гена %s-%s у пациета %s уже присутствует", diagnosisId, geneId, patientId));
    }

    @Override
    public Integer getHttpCode() {
        return 400;
    }
}
