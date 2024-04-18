package ru.vaganov.ResourceServer.exceptions;

import ru.vaganov.ResourceServer.exceptions.LehaimException;

import java.time.LocalDate;
import java.util.UUID;

public class PatientNotFoundException extends LehaimException {

    private final static String baseMessage = "Не найден пациент: ";
    private final static Integer HTTPCode = 404;

    public PatientNotFoundException(UUID id){
        super(baseMessage + "id: " + id.toString());
    }

    public PatientNotFoundException(String firstname, String lastname, String middlename){
        super(String.format("%s%s %s %s", baseMessage, firstname,lastname,middlename));
    }

    public PatientNotFoundException(String firstname, String lastname, String middlename, LocalDate birthdate){
        super(String.format("%s%s %s %s %s", baseMessage, firstname,lastname,middlename, birthdate));
    }

    public PatientNotFoundException(String message){
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return HTTPCode;
    }
}
