package com.devices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreationResponse {

    private Integer id;
    private String brand;
    private String name;
    private LocalDateTime creationTime;


}
