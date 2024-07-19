package com.devices.service;

import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceService implements IDeviceService {

    @Override
    public DeviceResponse addDevice(DeviceCreationRequest request) {
        return null;
    }

    @Override
    public Page<DeviceResponse> getAllDevices() {
        return null;
    }

    @Override
    public DeviceService findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Page<DeviceResponse> searchByBrand(String brand) {
        return null;
    }
}
