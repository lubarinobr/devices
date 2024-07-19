package com.devices.Fixtures;

import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import com.devices.entities.Device;

import java.time.LocalDateTime;

public class DeviceFixtures {

    public static DeviceCreationRequest newDeviceCreationRequest() {
        return new DeviceCreationRequest();
    }

    public static DeviceCreationRequest createNewDeviceRequest(String name, String brand) {
        return new DeviceCreationRequest(name, brand);
    }

    public static DeviceCreationRequest updateRequestDevice() {
        return new DeviceCreationRequest("update", "update");
    }

    public static DeviceResponse deviceResponse() {
        return new DeviceResponse(1, "test", "test", LocalDateTime.now());
    }

    public static Device device() {
        return new Device(1, "test", "test", LocalDateTime.now());
    }
}
