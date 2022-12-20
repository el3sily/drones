package com.musala.drones.services.implementation;

import com.musala.drones.commons.exceptions.MedicationNotFoundException;
import com.musala.drones.constants.ErrorMessages;
import com.musala.drones.dtos.medication.MedicationDTO;
import com.musala.drones.dtos.medication.RegisterMedicationDTO;
import com.musala.drones.entities.Medication;
import com.musala.drones.repositories.MedicationRepository;
import com.musala.drones.services.MedicationsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class MedicationsServiceImp implements MedicationsService {
    private MedicationRepository medicationRepository;

    MedicationsServiceImp(MedicationRepository medicationRepository){
        this.medicationRepository = medicationRepository;
    }

    @SneakyThrows
    @Override
    public MedicationDTO registerMedication(RegisterMedicationDTO medicationDTO, MultipartFile medicationImage) {
        Blob file = new SerialBlob(medicationImage.getBytes());
        Medication medication = Medication.builder()
                .code(medicationDTO.getCode())
                .name(medicationDTO.getName())
                .weight(medicationDTO.getWeight())
                .image(file)
                .build();
        this.medicationRepository.save(medication);

        return MedicationDTO.convertMedicationToDTO(medication);
    }

    @Override
    public List<Medication> getMedicationsByCodes(List<String> medicationsCodes) {
        List<Medication> medications = new ArrayList<>();
        medicationsCodes.forEach(code -> medications.add(this.getMedicationByCode(code)));
        return medications;
    }

    @Override
    public Double countMedicationsWeight(List<Medication> medications) {
        AtomicReference<Double> totalMedWeight = new AtomicReference<>(0.0);
        medications.forEach(medication -> totalMedWeight.updateAndGet(v -> v + medication.getWeight()));
        return totalMedWeight.get();
    }


    private Medication getMedicationByCode(String code){
        return Optional.ofNullable(medicationRepository.findByCode(code))
                .orElseThrow(() -> new MedicationNotFoundException(String.format("%s %s", ErrorMessages.MEDICATION_NOT_FOUND, code)));
    }
}
