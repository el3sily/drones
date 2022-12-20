package com.musala.drones.constants;

public class ErrorMessages {

    public static final String DRONE_SERIAL_NAME_LENGTH = "Invalid serialNumber length min(1) and max(100)";
    public static final String DRONE_SERIAL_NUMBER_NULL = "serialNumber is mandatory";
    public static final String DRONE_SERIAL_NUMBER_BLANK = "serialNumber is blank";


    public static final String MEDICATION_WEIGHT_NULL = "weight is mandatory";



    public static final String DRONE_NOT_FOUND = "Drone not found";
    public static final String MEDICATION_NOT_FOUND = "Medication not found";
    public static final String DRONE_HAS_NOT_MEDICATIONS = "Drone hasn't loaded with any medications";
    public static final String DRONE_CANT_LOAD_LOW_BATTERY = "Drone can't load medications because battery is less than 25.0%";
    public static final String DRONE_ALREADY_LOADED = "Drone current state is loaded can't load medications";
    public static final String DRONE_WEIGHT_LIMIT_LESS_THAN_MEDICATIONS = "Drone weight limit is less than medications";
}

