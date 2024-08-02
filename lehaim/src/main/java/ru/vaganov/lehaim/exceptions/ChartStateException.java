package ru.vaganov.lehaim.exceptions;

import org.springframework.http.HttpStatus;

public class ChartStateException extends LehaimException{

    public ChartStateException(String msg){
        super(msg);
    }

    @Override
    public Integer getHttpCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
