package ru.vaganov.ResourceServer.exceptions;

public abstract class LehaimException extends RuntimeException{

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
}
