package com.musala.drones.commons.exceptions;

public class MedicationNotFoundException extends RuntimeException{
    public MedicationNotFoundException(String message) {
        super(message);
    }
}
