package com.devices.service;

import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import com.devices.entities.Device;
import com.devices.repository.DeviceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
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
    private final ObjectMapper objectMapper;

    @Override
    public DeviceResponse addDevice(DeviceCreationRequest request) {
        return DeviceResponse.build(deviceRepository.save(request.build()));
    }

    @Override
    public Page<DeviceResponse> findAll(Pageable pageable) {
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
    public Page<DeviceResponse> findByBrand(String brand, Pageable pageable) {
        return deviceRepository.findByBrand(brand, pageable).map(DeviceResponse::build);
    }

    @Override
    public DeviceResponse update(Integer id, DeviceCreationRequest request) throws Exception {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
        device.setBrand(request.getBrand());
        device.setName(request.getName());
        deviceRepository.save(device);
        return DeviceResponse.build(device);
    }

    @Override
    public DeviceResponse updatePartial(Integer id, JsonPatch patch) throws Exception {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
        JsonNode updatedJson = patch.apply(objectMapper.convertValue(device, JsonNode.class));
        Device updateEntity = objectMapper.treeToValue(updatedJson, Device.class);
        return DeviceResponse.build(updateEntity);
    }
}
