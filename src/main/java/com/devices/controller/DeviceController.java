package com.devices.controller;

import com.devices.dto.DeviceCreationRequest;
import com.devices.enums.FindOperation;
import com.devices.dto.DeviceResponse;
import com.devices.service.IDeviceService;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

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
            case FindOperation.BRAND -> deviceService.findByBrand(searchParameters.get(findOperation.getOperator()), pageable);
            default -> deviceService.findAll(pageable);
        };

        return ResponseEntity.ok(responsePage);
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> save(@Valid @RequestBody DeviceCreationRequest request) {
        final DeviceResponse deviceResponse = deviceService.addDevice(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .queryParam("id", deviceResponse.getId())
                .buildAndExpand(deviceResponse.getId())
                .toUri();

        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location.toString()).body(deviceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") Integer id) {
        deviceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateEntity(
            @PathVariable(value = "id") Integer id,
            @Valid @RequestBody DeviceCreationRequest request
    ) throws Exception {
        DeviceResponse response = deviceService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DeviceResponse> updatePartial(
            @PathVariable(value = "id") Integer id,
            @RequestBody JsonPatch patch
    ) throws Exception {
        DeviceResponse response = deviceService.updatePartial(id, patch);
        return ResponseEntity.ok(response);
    }
}
