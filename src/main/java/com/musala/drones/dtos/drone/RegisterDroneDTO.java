package com.musala.drones.dtos.drone;

import com.musala.drones.constants.ErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Register Drone", description = "Register Drone")
public class RegisterDroneDTO {

    @NotNull(message = ErrorMessages.DRONE_SERIAL_NUMBER_NULL)
    @NotBlank(message = ErrorMessages.DRONE_SERIAL_NUMBER_BLANK)
    @Size(min = 1, max = 100, message = ErrorMessages.DRONE_SERIAL_NAME_LENGTH)
    @Schema(name = "serialNumber", description = "serial number", requiredMode = Schema.RequiredMode.REQUIRED
    , example = "drone-serial-123")
    String serialNumber;

    @NotNull(message = "model is mandatory")
    @NotBlank(message = "model is blank")
    @Schema(name = "model", description = "drone model", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "LightWeight")
    String model;

    @NotNull(message = "weight is mandatory")
    @DecimalMin(value = "0.1", message= "weight min is 0.1")
    @DecimalMax(value = "500.0", message= "weight max is 500")
    @Schema(name = "weight", description = "drone weight", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "400")
    Double weight;

    @NotNull(message = "batteryCapacity is mandatory")
    @DecimalMin(value = "1.0", message= "batteryCapacity can't be less than 1")
    @DecimalMax(value = "100.0", message= "batteryCapacity can't be more than 100")
    @Schema(name = "batteryCapacity", description = "battery capacity percentage", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "100")
    Double batteryCapacity;

    @NotNull(message = "state is mandatory")
    @NotBlank(message = "state is blank")
    @Schema(name = "state", description = "state", requiredMode = Schema.RequiredMode.REQUIRED
            , example = "IDLE")
    String state;
}
