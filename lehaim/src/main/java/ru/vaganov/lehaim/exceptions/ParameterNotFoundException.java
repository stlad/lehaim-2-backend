package ru.vaganov.lehaim.exceptions;

public class ParameterNotFoundException extends LehaimException{
    private final static String baseMessage = "Не найден параметр: ";

    public ParameterNotFoundException(Long id){
        super(baseMessage+id);
    }


    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
