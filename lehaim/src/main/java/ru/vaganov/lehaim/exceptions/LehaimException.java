package ru.vaganov.lehaim.exceptions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j @Data
public abstract class LehaimException extends RuntimeException{

    protected String logMessage;
    public LehaimException(){
        super();
    }

    public LehaimException(String msg){
        super(msg);
    }

    public abstract Integer getHttpCode();

    public LehaimErrorDTO getErrorDTO() {
        String sourceClass = this.getStackTrace()[0].getClassName();
        String sourceMethod = this.getStackTrace()[0].getMethodName();
        return new LehaimErrorDTO(getHttpCode(), getMessage(), this.getClass().getSimpleName(),
                sourceClass + " | "+ sourceMethod);
    }

    public final void log(){
        String cause =getStackTrace()[0].getClassName() + " | " + getStackTrace()[0].getMethodName();
        String msg = getLogMessage() == null ? getMessage() : getLogMessage();
        log.error(msg + " | " + getClass().getName()+" | "+cause);
    }
}
