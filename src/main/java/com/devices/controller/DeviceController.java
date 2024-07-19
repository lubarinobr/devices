package com.devices.controller;

import com.devices.dto.DeviceResponse;
import com.devices.service.IDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final IDeviceService deviceService;


    @GetMapping
    public ResponseEntity<List<DeviceResponse>> findAllDevices() {
        List<DeviceResponse> deviceResponses = List.of(new DeviceResponse(1, "test", "test", LocalDateTime.now()));
        return ResponseEntity.ok(deviceResponses);
    }
}
