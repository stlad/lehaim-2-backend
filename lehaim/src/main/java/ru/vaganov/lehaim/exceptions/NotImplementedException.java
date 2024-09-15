package ru.vaganov.lehaim.exceptions;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends LehaimException {
    private final static String baseMessage = "Данный функционал еще не реализован";

    public NotImplementedException() {
        super(baseMessage);
        setLogMessage(baseMessage);
    }

    @Override
    public Integer getHttpCode() {
        return HttpStatus.NOT_IMPLEMENTED.value();
    }
}
