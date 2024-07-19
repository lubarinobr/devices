package com.devices.service;

import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import com.devices.entities.Device;
import com.devices.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceService implements IDeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public DeviceResponse addDevice(DeviceCreationRequest request) {
        return DeviceResponse.build(deviceRepository.save(request.build()));
    }

    @Override
    public Page<DeviceResponse> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable).map(DeviceResponse::build);
    }

    @Override
    public Page<DeviceResponse> findById(Integer id, Pageable pageable) {
        return deviceRepository.findById(id, pageable).map(DeviceResponse::build);
    }

    @Override
    public void deleteById(Integer id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public Page<DeviceResponse> searchByBrand(String brand, Pageable pageable) {
        return deviceRepository.findByBrand(brand, pageable).map(DeviceResponse::build);
    }
}
