package ru.vaganov.ResourceServer.exceptions;

import java.time.LocalDate;
import java.util.UUID;

public class PatientExistsException extends LehaimException{
    private final static String baseMessage = "Уже существует пациент: ";

    public PatientExistsException(UUID id){
        super(baseMessage + "id: " + id.toString());
    }

    public PatientExistsException(String firstname, String lastname, String middlename){
        super(String.format("%s%s %s %s", baseMessage, firstname,lastname,middlename));
    }

    public PatientExistsException(String firstname, String lastname, String middlename, LocalDate birthdate){
        super(String.format("%s%s %s %s %s", baseMessage, firstname,lastname,middlename, birthdate));
    }

    public PatientExistsException(String message){
        super(baseMessage + message);
    }

    @Override
    public Integer getHttpCode() {
        return 400;
    }

}
