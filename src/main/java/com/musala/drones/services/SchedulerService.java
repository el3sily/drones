package com.musala.drones.services;

import com.musala.drones.entities.Drone;
import com.musala.drones.repositories.DroneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;


@Configuration
@EnableScheduling
@Slf4j
public class SchedulerService {

    private DroneRepository droneRepository;

    public SchedulerService(DroneRepository droneRepository){
        this.droneRepository = droneRepository;
    }


    @Scheduled(fixedDelayString = "${drone.periodicCheck}")
    public void checkDroneBatteryLevel(){
        List<Drone> drones = droneRepository.findAll();

        drones.forEach(drone ->
                log.info("Drone with serial number {} battery level is {}%", drone.getSerialNumber(), drone.getBatteryCapacity()));
    }
}
