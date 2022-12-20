package com.musala.drones.services;

import com.musala.drones.dtos.medication.MedicationDTO;
import com.musala.drones.dtos.medication.RegisterMedicationDTO;
import com.musala.drones.entities.Medication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MedicationsService {
    MedicationDTO registerMedication(RegisterMedicationDTO medicationDTO, MultipartFile medicationImage);

    List<Medication> getMedicationsByCodes(List<String> medicationsCodes);

    Double countMedicationsWeight(List<Medication> medications);
}
