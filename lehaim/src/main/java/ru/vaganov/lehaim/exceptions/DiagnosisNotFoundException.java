package ru.vaganov.lehaim.exceptions;

public class DiagnosisNotFoundException extends LehaimException{
    private final static String baseMessage = "Не найден диагноз: ";

    public DiagnosisNotFoundException(Integer id){
        super(baseMessage + id);
    }

    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
