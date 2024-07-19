package com.devices.service;

import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDeviceService {

    public DeviceResponse addDevice(DeviceCreationRequest request);
    public Page<DeviceResponse> findAll(Pageable pageable);
    public Page<DeviceResponse> findById(Integer id, Pageable pageable);
    public void deleteById(Integer id);
    public Page<DeviceResponse> findByBrand(String brand, Pageable pageable);

}
