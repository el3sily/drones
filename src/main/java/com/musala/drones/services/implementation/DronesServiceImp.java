package com.musala.drones.services.implementation;

import com.musala.drones.commons.exceptions.DroneNotFoundException;
import com.musala.drones.commons.exceptions.GeneralException;
import com.musala.drones.constants.ErrorMessages;
import com.musala.drones.dtos.drone.DroneDTO;
import com.musala.drones.dtos.drone.RegisterDroneDTO;
import com.musala.drones.entities.Drone;
import com.musala.drones.entities.Medication;
import com.musala.drones.entities.enums.DroneModel;
import com.musala.drones.entities.enums.DroneState;
import com.musala.drones.repositories.DroneRepository;
import com.musala.drones.services.DronesService;
import com.musala.drones.services.MedicationsService;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DronesServiceImp implements DronesService {

    private DroneRepository droneRepository;
    private MedicationsService medicationsService;

    DronesServiceImp(DroneRepository droneRepository, MedicationsService medicationsService){
        this.droneRepository = droneRepository;
        this.medicationsService = medicationsService;
    }


    @Override
    public DroneDTO registerDrone(RegisterDroneDTO droneDTO) {
        Drone drone = Drone.builder()
                .serialNumber(droneDTO.getSerialNumber())
                .state(DroneState.valueOf(droneDTO.getState()))
                .model(DroneModel.valueOf(droneDTO.getModel()))
                .weight(droneDTO.getWeight())
                .batteryCapacity(droneDTO.getBatteryCapacity())
                .build();
        this.droneRepository.save(drone);

        return DroneDTO.convertDroneToDTO(drone);
    }


    @Override
    public DroneDTO loadDroneWithMedications(String serialNumber, List<String> medicationsCodes) {
        Drone drone = this.getDrone(serialNumber);
        if(drone.getBatteryCapacity() < 25)
            throw new GeneralException(ErrorMessages.DRONE_CANT_LOAD_LOW_BATTERY);
        if(DroneState.LOADED.equals(drone.getState()))
            throw new GeneralException(ErrorMessages.DRONE_ALREADY_LOADED);
        List<Medication> medications = this.medicationsService.getMedicationsByCodes(medicationsCodes);

        this.checkAvailableDroneWeight(drone.getWeight(), medications);

        drone.setState(DroneState.LOADED);
        drone.setMedications(medications);
        droneRepository.save(drone);
        return DroneDTO.convertDroneToDTO(drone);
    }

    @Override
    public JSONObject getDroneBatteryLevel(String serialNumber) {
        Drone drone = this.getDrone(serialNumber);
        JSONObject json = new JSONObject();
        json.put("batteryLevel", drone.getBatteryCapacity()+"%");

        return json;
    }

    @Override
    public List<DroneDTO> availableDronesForLoading() {
        List<Drone> idleDrones = this.droneRepository.findAllByState(DroneState.IDLE);

        return idleDrones.stream().map(DroneDTO::convertDroneToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DroneDTO checkDroneLoadedMedications(String serialNumber) {
        Drone drone = this.getDrone(serialNumber);

        if(CollectionUtils.isEmpty(drone.getMedications())){
            throw new GeneralException(ErrorMessages.DRONE_HAS_NOT_MEDICATIONS);
        }
        return DroneDTO.convertDroneToDTO(drone);
    }


    private Drone getDrone(String serialNumber){
        return Optional.ofNullable(droneRepository.findBySerialNumber(serialNumber))
                .orElseThrow(() -> new DroneNotFoundException(ErrorMessages.DRONE_NOT_FOUND));
    }

    private void checkAvailableDroneWeight(Double droneWeightLimit, List<Medication> medications){
        Double totalMedicationsWeight = this.medicationsService.countMedicationsWeight(medications);
        if (droneWeightLimit.compareTo(totalMedicationsWeight) < 0)
            throw new GeneralException(ErrorMessages.DRONE_WEIGHT_LIMIT_LESS_THAN_MEDICATIONS);
    }
}
