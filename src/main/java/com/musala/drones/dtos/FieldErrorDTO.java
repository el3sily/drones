package com.musala.drones.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FieldErrorDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime date;
    private String message;

    public FieldErrorDTO(String message){
        date = LocalDateTime.now();
        this.message = message;
    }

}
