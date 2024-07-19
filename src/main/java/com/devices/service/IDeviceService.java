package com.devices.service;

import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDeviceService {

    DeviceResponse addDevice(DeviceCreationRequest request);
    Page<DeviceResponse> findAll(Pageable pageable);
    Page<DeviceResponse> findById(Integer id, Pageable pageable);
    void deleteById(Integer id);
    Page<DeviceResponse> findByBrand(String brand, Pageable pageable);
    DeviceResponse update(Integer id, DeviceCreationRequest request) throws Exception;
    DeviceResponse updatePartial(Integer id, JsonPatch patch) throws Exception;
}
