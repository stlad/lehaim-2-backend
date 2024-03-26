package ru.vaganov.ResourceServer.controllers.handlers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vaganov.ResourceServer.exceptions.ValidationException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException ex){
        log.error(ex.getMessage() + " | " + ex.getClass().getName());
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getClass().getName()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(IllegalArgumentException ex){
        log.error(ex.getMessage() + " | " + ex.getClass().getName());
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getClass().getName()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(ValidationException ex){
        log.error(ex.getMessage() + " | " + ex.getClass().getName());
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getClass().getName()), HttpStatus.BAD_REQUEST);
    }
}

@Data
@AllArgsConstructor
class ErrorDTO{
    private Integer code;
    private String msg;
    private String error;
}