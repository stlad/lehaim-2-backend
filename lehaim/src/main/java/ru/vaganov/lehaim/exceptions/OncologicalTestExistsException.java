package ru.vaganov.lehaim.exceptions;

import java.time.LocalDate;
import java.util.UUID;

public class OncologicalTestExistsException extends LehaimException{
    private final static String baseMessage = "Уже существует обследование: ";

    public OncologicalTestExistsException(UUID ownerId, LocalDate testDate){
        super(baseMessage+"от: "+testDate);
        setLogMessage(baseMessage+"у пациента: "+ownerId+" от: "+testDate);
    }

    @Override
    public Integer getHttpCode() {
        return 400;
    }
}
