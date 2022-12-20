package com.musala.drones.apis;

import com.musala.drones.dtos.medication.MedicationDTO;
import com.musala.drones.dtos.medication.RegisterMedicationDTO;
import com.musala.drones.entities.Medication;
import com.musala.drones.services.MedicationsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/medication")
@Tag(name = "Medication", description = "Medication API")
@Validated
public class MedicationsController {

    private MedicationsService medicationsService;

    MedicationsController(MedicationsService medicationsService){
        this.medicationsService = medicationsService;
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MedicationDTO.class)))
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "not found")
    public ResponseEntity<Medication> registerMedication(@Parameter(name = "medication", description = "medication to register", required = true)
                                                             @Valid @RequestPart("medication") RegisterMedicationDTO medication,
                                                         @Parameter(name = "medicationFile", description = "medication file", required = true)
                                                         @RequestPart("medicationFile") @NotNull(message = "medicationFile is mandatory") MultipartFile medicationFile){

        return new ResponseEntity(this.medicationsService.registerMedication(medication, medicationFile), HttpStatus.CREATED);
    }
}
