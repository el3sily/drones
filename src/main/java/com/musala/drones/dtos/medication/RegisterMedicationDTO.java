package com.musala.drones.dtos.medication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Register Medication", description = "Register Medication")
public class RegisterMedicationDTO {

    @NotNull(message = "code is mandatory")
    @NotBlank(message="code is blank")
    @Pattern(regexp = "^[a-zA-Z0-9_]*", message="code only allowed upper case letters, numbers and (_)")
    @Schema(name = "code", description = "code", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "CODE-123")
    private String code;


    @NotNull(message = "name is mandatory")
    @NotBlank(message="name is blank")
    @Pattern(regexp = "^[A-Z0-9_-]*", message="name only allowed letters, numbers, (_) and (-)")
    @Schema(name = "name", description = "name", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "medical_scratch")
    private String name;

    @NotNull(message= "weight is mandatory")
    @DecimalMin(value = "1.0", message= "weight can't be less than 1")
    @DecimalMax(value = "500.0", message= "weight can't be greater than 500")
    private Double weight;
}
