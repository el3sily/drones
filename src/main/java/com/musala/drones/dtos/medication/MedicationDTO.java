package com.musala.drones.dtos.medication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.musala.drones.entities.Medication;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Blob;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "code", "name", "weight", "image"})
@Data
@Builder
@Schema(name = "Medication", description = "Medication")
public class MedicationDTO {

    @Schema(name = "id", description = "id", example = "1")
    private Integer id;

    @Schema(name = "name", description = "name", example = "MEDICAL-SCRATCH")
    private String name;

    @Schema(name = "code", description = "code", example = "CD-MED")
    private String code;

    @Schema(name = "weight", description = "weight", example = "50.0")
    private Double weight;

    @Schema(name = "image", description = "image")
    private Blob image;

    public static MedicationDTO convertMedicationToDTO(Medication medication){
        return MedicationDTO.builder()
                .id(medication.getId())
                .code(medication.getCode())
                .name(medication.getName())
                .image(medication.getImage())
                .build();
    }
}
