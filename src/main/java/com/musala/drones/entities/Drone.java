package com.musala.drones.entities;

import com.musala.drones.entities.enums.DroneModel;
import com.musala.drones.entities.enums.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drone")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drone {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    DroneModel model;

    @Column(name = "weight_limit", nullable = false)
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private DroneState state;

    @Column(name = "battery_capacity")
    private Double batteryCapacity;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "drone_id")
    private List<Medication> medications;
}
