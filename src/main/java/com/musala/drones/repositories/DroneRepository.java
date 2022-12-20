package com.musala.drones.repositories;

import com.musala.drones.entities.Drone;
import com.musala.drones.entities.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {

    Drone findBySerialNumber(String serialNumber);

    List<Drone> findAllByState(DroneState state);
}
