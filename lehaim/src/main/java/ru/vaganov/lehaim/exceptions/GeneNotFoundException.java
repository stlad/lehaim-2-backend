package ru.vaganov.lehaim.exceptions;

public class GeneNotFoundException extends LehaimException{
    private final static String baseMessage = "Не найден ген: ";

    public GeneNotFoundException(Long id){
        super(baseMessage+id);
    }


    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
