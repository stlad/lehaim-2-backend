package ru.vaganov.lehaim.exceptions;


public class ValidationException extends RuntimeException{
    public ValidationException(String msg){
        super(msg);
    }

    public ValidationException(){
        super();
    }
}
