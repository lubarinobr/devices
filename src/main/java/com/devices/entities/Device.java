package com.devices.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Device {

    private Integer id;
    private String name;
    private String brand;
    private LocalDateTime creation;

    private Device() {}
}
