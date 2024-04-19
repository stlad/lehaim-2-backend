package ru.vaganov.ResourceServer.exceptions;

public class OncologicalTestNotFoundException extends LehaimException{

    private final static String baseMessage = "Не найдено обследование: ";

    public OncologicalTestNotFoundException(Long id){
        super(baseMessage+id);
    }


    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
