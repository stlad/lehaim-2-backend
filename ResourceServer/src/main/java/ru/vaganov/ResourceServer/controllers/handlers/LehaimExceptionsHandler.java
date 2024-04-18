package ru.vaganov.ResourceServer.controllers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vaganov.ResourceServer.exceptions.LehaimErrorDTO;
import ru.vaganov.ResourceServer.exceptions.LehaimException;

@RestControllerAdvice
@Slf4j
public class LehaimExceptionsHandler {

    @ExceptionHandler(LehaimException.class)
    public ResponseEntity<LehaimErrorDTO> handleLehaimExceptions(LehaimException ex){
        String cause =ex.getStackTrace()[0].getClassName() + " | " + ex.getStackTrace()[0].getMethodName();
        log.error(ex.getMessage() + " | " + ex.getClass().getName()+" | "+cause);
        return new ResponseEntity<>(ex.getErrorDTO(), HttpStatus.valueOf(ex.getHttpCode()));
    }
}




