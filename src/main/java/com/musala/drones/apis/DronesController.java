package com.musala.drones.apis;

import com.musala.drones.constants.ErrorMessages;
import com.musala.drones.dtos.drone.DroneDTO;
import com.musala.drones.dtos.drone.RegisterDroneDTO;
import com.musala.drones.entities.Drone;
import com.musala.drones.services.DronesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/drone")
@Tag(name = "Drone", description = "Drone API")
@Validated
@Slf4j
public class DronesController {

    private DronesService dronesService;

    DronesController(DronesService dronesService){
        this.dronesService = dronesService;
    }


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register new Drone")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = DroneDTO.class, type = "droneDTO")))
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "409", description = "conflict")
    ResponseEntity<Drone> registerDrone(@Parameter(name = "registerDroneDTO", description = "drone to register")
                                        @RequestBody @Valid RegisterDroneDTO registerDroneDTO){
        return new ResponseEntity(dronesService.registerDrone(registerDroneDTO), HttpStatus.CREATED);
    }

    @PostMapping("/load/{serialNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Register new Drone")
    @ApiResponse(responseCode = "200", description = "", content = @Content(schema = @Schema(implementation = DroneDTO.class, type = "droneDTO")))
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "not found")
    ResponseEntity<DroneDTO> getDrone(
            @Parameter(name = "serialNumber", description = "drone serial number", example = "serial-num-123", required = true)
            @PathVariable("serialNumber") @NotNull(message = ErrorMessages.DRONE_SERIAL_NUMBER_NULL)
            @NotBlank(message = ErrorMessages.DRONE_SERIAL_NUMBER_BLANK) String serialNumber,
            @Parameter(name = "medicationsCodes", description = "medications codes to register", required = true,
                    example = "[" +
                    "\"CODE-123," +
                    "\"CODE-1234" +
                    "]")
            @RequestBody List<String> medicationsCodes){
        return new ResponseEntity(dronesService.loadDroneWithMedications(serialNumber, medicationsCodes), HttpStatus.OK);
    }

    @GetMapping("/loadedMedications/{serialNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get drone with it's medications")
    @ApiResponse(responseCode = "200", description = "", content = @Content(schema = @Schema(implementation = DroneDTO.class, type = "droneDTO")))
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "not found")
    ResponseEntity<DroneDTO> checkDroneLoadedMedications(@Parameter(name = "serialNumber", description = "drone serial number", example = "serial-num-123", required = true)
                                                         @PathVariable("serialNumber") @NotNull(message = ErrorMessages.DRONE_SERIAL_NUMBER_NULL)
                                                         @NotBlank(message = ErrorMessages.DRONE_SERIAL_NUMBER_BLANK) String serialNumber){
        return new ResponseEntity(this.dronesService.checkDroneLoadedMedications(serialNumber), HttpStatus.CREATED);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all available drones for loading")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DroneDTO[].class, type = "droneDTO")))
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "not found")
    ResponseEntity<List<DroneDTO>> getAvailableDroneForLoading(){
        return new ResponseEntity(this.dronesService.availableDronesForLoading(), HttpStatus.OK);
    }

    @GetMapping("/batteryLevel/{serialNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get drone battery level")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JSONObject.class, example = "{\n" +
            "  \"batteryLevel\": \"100.0%\"\n" +
            "}")))
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "not found")
    ResponseEntity<JSONObject> checkDroneBatteryLevel(@Parameter(name = "serialNumber", description = "drone serial number", example = "serial-num-123", required = true)
                                                      @PathVariable("serialNumber") @NotNull(message = ErrorMessages.DRONE_SERIAL_NUMBER_NULL)
                                                      @NotBlank(message = ErrorMessages.DRONE_SERIAL_NUMBER_BLANK) String serialNumber){
        return new ResponseEntity(this.dronesService.getDroneBatteryLevel(serialNumber), HttpStatus.OK);
    }

}
