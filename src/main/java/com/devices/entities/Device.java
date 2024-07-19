package com.devices.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String brand;
    private LocalDateTime creation = LocalDateTime.now();

    private Device() {}
}
