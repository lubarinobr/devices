package com.devices.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreationRequest{

    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "brand is required")
    String brand;

}
