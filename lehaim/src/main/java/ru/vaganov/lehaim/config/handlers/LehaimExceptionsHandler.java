package ru.vaganov.lehaim.config.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vaganov.lehaim.exceptions.LehaimErrorDTO;
import ru.vaganov.lehaim.exceptions.LehaimException;

@RestControllerAdvice
@Slf4j
public class LehaimExceptionsHandler {

    @ExceptionHandler(LehaimException.class)
    public ResponseEntity<LehaimErrorDTO> handleLehaimExceptions(LehaimException ex){
        ex.log();
        return new ResponseEntity<>(ex.getErrorDTO(), HttpStatus.valueOf(ex.getHttpCode()));
    }
}




