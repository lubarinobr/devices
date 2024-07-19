package com.devices.dto;

import com.devices.entities.Device;
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

    public Device build() {
        return new Device(this.brand, this.brand);
    }
}
