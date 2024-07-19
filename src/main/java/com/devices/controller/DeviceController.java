package com.devices.controller;

import com.devices.enums.FindOperation;
import com.devices.dto.DeviceResponse;
import com.devices.service.IDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final IDeviceService deviceService;


    @GetMapping
    public ResponseEntity<Page<DeviceResponse>> findDeviceByParam(@RequestParam Map<String, String> searchParameters)
    {
        final int page = Integer.parseInt(searchParameters.getOrDefault("page", "0"));
        final int size = Integer.parseInt(searchParameters.getOrDefault("size", "5"));

        final Pageable pageable = Pageable.ofSize(size).withPage(page);

        final FindOperation findOperation = Arrays.stream(FindOperation.values())
                .filter(name -> searchParameters.containsKey(name.getOperator()))
                .findFirst().orElse(FindOperation.EMPTY);

        Page<DeviceResponse> responsePage = switch (findOperation) {
            case FindOperation.ID -> deviceService.findById(Integer.parseInt(searchParameters.get(findOperation.getOperator())), pageable);
            case FindOperation.BRAND -> deviceService.searchByBrand(searchParameters.get(findOperation.getOperator()), pageable);
            default -> deviceService.getAllDevices(pageable);
        };

        return ResponseEntity.ok(responsePage);
    }
}
