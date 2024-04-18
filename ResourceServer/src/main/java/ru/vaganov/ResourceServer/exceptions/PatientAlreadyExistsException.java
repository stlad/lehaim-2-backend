package ru.vaganov.ResourceServer.exceptions;

import java.time.LocalDate;
import java.util.UUID;

public class PatientAlreadyExistsException extends LehaimException{
    private final static String baseMessage = "Уже существует пациент: ";
    private final static Integer HTTPCode = 400;

    public PatientAlreadyExistsException(UUID id){
        super(baseMessage + "id: " + id.toString());
    }

    public PatientAlreadyExistsException(String firstname, String lastname, String middlename){
        super(String.format("%s%s %s %s", baseMessage, firstname,lastname,middlename));
    }

    public PatientAlreadyExistsException(String firstname, String lastname, String middlename, LocalDate birthdate){
        super(String.format("%s%s %s %s %s", baseMessage, firstname,lastname,middlename, birthdate));
    }

    public PatientAlreadyExistsException(String message){
        super(baseMessage + message);
    }

    @Override
    public Integer getHttpCode() {
        return HTTPCode;
    }

}
