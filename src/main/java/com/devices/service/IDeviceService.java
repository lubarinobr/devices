package com.devices.service;

import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import org.springframework.data.domain.Page;

public interface IDeviceService {

    public DeviceResponse addDevice(DeviceCreationRequest request);
    public Page<DeviceResponse> getAllDevices();
    public DeviceService findById(Integer id);
    public void deleteById(Integer id);
    public Page<DeviceResponse> searchByBrand(String brand);

}
