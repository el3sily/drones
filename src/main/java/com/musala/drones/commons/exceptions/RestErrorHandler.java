package com.musala.drones.commons.exceptions;

import com.musala.drones.dtos.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleValidationsException(MethodArgumentNotValidException ex) {
        List<FieldErrorDTO> fieldErrorDTOS = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorDTO(error.getDefaultMessage()))
                .collect(Collectors.toList());
        return fieldErrorDTOS.get(0);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Object handleNotSupportedMethodException(HttpRequestMethodNotSupportedException ex) {
        return new FieldErrorDTO(ex.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public FieldErrorDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return  new FieldErrorDTO(ex.getMessage());
    }

    @ExceptionHandler({GeneralException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleGeneralException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(new FieldErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleInvalidRequestParameterException(ConstraintViolationException ex) {
        final List<FieldErrorDTO> fieldErrorDTOS = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .map(message -> new FieldErrorDTO(message)).collect(Collectors.toList());
        return fieldErrorDTOS.get(0);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleMissingRequestParameterException(MissingServletRequestParameterException ex) {
        return new FieldErrorDTO(ex.getParameterName() + " is mandatory");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public FieldErrorDTO handleIllegalArgumentException(IllegalArgumentException ex){
        return new FieldErrorDTO(ex.getMessage());
    }


    @ExceptionHandler({DroneNotFoundException.class, MedicationNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new FieldErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<Object> handleHibernateConstraintViolationException(org.hibernate.exception.ConstraintViolationException ex) {
        FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(ex.getConstraintName() + " " + ex.getMessage());

        if (ex.getConstraintName().contains("uk_drone_serial"))
            fieldErrorDTO.setMessage("Drone already exist");

        else if (ex.getConstraintName().contains("uk_medication_code"))
            fieldErrorDTO.setMessage("Medication already exist");


        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(fieldErrorDTO);
    }
}
