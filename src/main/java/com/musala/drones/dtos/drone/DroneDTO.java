package com.musala.drones.dtos.drone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musala.drones.dtos.medication.MedicationDTO;
import com.musala.drones.entities.Drone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Drone", description = "Drone")
public class DroneDTO {

    @Schema(name = "id", description = "id", example = "1")
    Integer id;

    @Schema(name = "serialNumber", description = "serial number", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "drone-serial-123")
    String serialNumber;

    @Schema(name = "model", description = "drone model", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "LightWeight")
    String model;

    @Schema(name = "weight", description = "drone weight", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "400")
    Double weight;

    @Schema(name = "batteryCapacity", description = "battery capacity", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "100%")
    Double batteryCapacity;

    @Schema(name = "state", description = "state", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "IDLE")
    String state;

    @Schema(name = "medications", description = "medications", requiredMode = Schema.RequiredMode.REQUIRED)
    List<MedicationDTO> medications;


    public static DroneDTO convertDroneToDTO(Drone drone){
        return DroneDTO.builder()
                .id(drone.getId())
                .serialNumber(drone.getSerialNumber())
                .model(drone.getModel().name())
                .weight(drone.getWeight())
                .batteryCapacity(drone.getBatteryCapacity())
                .state(drone.getState().name())
                .medications(CollectionUtils.isEmpty(drone.getMedications()) ? null :
                        drone.getMedications().stream().map(MedicationDTO::convertMedicationToDTO)
                                .collect(Collectors.toList()))
                .build();
    }
}
