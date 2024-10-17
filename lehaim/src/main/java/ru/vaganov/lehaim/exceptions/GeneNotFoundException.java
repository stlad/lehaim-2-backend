package ru.vaganov.lehaim.exceptions;

public class GeneNotFoundException extends LehaimException{
    private final static String baseMessage = "Не найден ген: ";

    public GeneNotFoundException(Long id){
        super(baseMessage+id);
    }
    public GeneNotFoundException(Integer diagnosisId, Long geneId){
        super(String.format("Для диагноза %s не может быть гена %s", diagnosisId, geneId));
    }


    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
