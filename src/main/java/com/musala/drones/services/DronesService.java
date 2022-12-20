package com.musala.drones.services;

import com.musala.drones.dtos.drone.DroneDTO;
import com.musala.drones.dtos.drone.RegisterDroneDTO;

import java.util.List;

public interface DronesService {
    DroneDTO registerDrone(RegisterDroneDTO droneDTO);

    DroneDTO loadDroneWithMedications(String serialNumber, List<String> medicationsCodes);

    Object getDroneBatteryLevel(String serialNumber);

    List<DroneDTO> availableDronesForLoading();

    DroneDTO checkDroneLoadedMedications(String serialNumber);
}
