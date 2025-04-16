package ru.vaganov.lehaim.exceptions;

import ru.vaganov.lehaim.patient.entity.Patient;

import java.time.LocalDate;
import java.util.UUID;

public class PatientExistsException extends LehaimException {
    private final static String baseMessage = "Уже существует пациент: ";

    public PatientExistsException(UUID id) {
        super(baseMessage + "id: " + id.toString());
    }

    public PatientExistsException(String firstname, String lastname, String middlename) {
        super(String.format("%s%s %s %s", baseMessage, firstname, lastname, middlename));
    }

    public PatientExistsException(String firstname, String lastname, String middlename, LocalDate birthdate) {
        super(String.format("%s%s %s %s %s", baseMessage, firstname, lastname, middlename, birthdate));
    }

    public PatientExistsException(String message) {
        super(baseMessage + message);
    }

    public PatientExistsException(Patient patient) {
        super((String.format("%s%s %s %s %s", baseMessage,
                patient.getName(),
                patient.getLastname(),
                patient.getPatronymic(),
                patient.getBirthdate())));
    }

    @Override
    public Integer getHttpCode() {
        return 400;
    }

}
