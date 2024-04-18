package ru.vaganov.ResourceServer.exceptions;

public class DiagnosisNotFoundException extends LehaimException{
    private final static String baseMessage = "Не найден диагноз: ";
    private final static Integer HTTPCode = 404;

    public DiagnosisNotFoundException(Integer id){
        super(baseMessage + id);
    }

    @Override
    public Integer getHttpCode() {
        return HTTPCode;
    }
}
