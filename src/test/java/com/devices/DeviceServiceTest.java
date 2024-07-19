package com.devices;

import com.devices.Fixtures.DeviceFixtures;
import com.devices.Fixtures.JsonPatchFixture;
import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import com.devices.entities.Device;
import com.devices.repository.DeviceRepository;
import com.devices.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DeviceServiceTest {

    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;
    private final ObjectMapper objectMapper;


    public DeviceServiceTest() {
        this.deviceRepository = Mockito.mock(DeviceRepository.class);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.deviceService = new DeviceService(this.deviceRepository, this.objectMapper);
    }

    @Test
    @DisplayName("When execute the update operation should return a entity with the new values")
    public void testUpdateDevice() throws Exception {
        DeviceCreationRequest request = DeviceFixtures.updateRequestDevice();
        Device device = DeviceFixtures.device();;
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        DeviceResponse response = deviceService.update(1, request);
        assertThat(response.getBrand(), CoreMatchers.equalTo(request.getBrand()));
        assertThat(response.getName(), CoreMatchers.equalTo(request.getName()));
        assertThat(response.getId(), CoreMatchers.equalTo(response.getId()));
        assertThat(response.getCreationTime(), CoreMatchers.equalTo(device.getCreation()));
    }

    @Test
    @DisplayName("When execute the update with a id that doesn't exist should throws NotFound exception")
    public void userNotFoundUpdate() {
        Assertions.assertThrows(Exception.class, () -> {
            when(deviceRepository.findById(any())).thenReturn(Optional.empty());
            deviceService.update(1, DeviceFixtures.updateRequestDevice());
        });
    }

    @Test
    @DisplayName("When execute the partial update with a id that should update only the field brand")
    public void updatePartialDevice() throws Exception {
        JsonPatch patch = JsonPatchFixture.updateField("brand", "\"newValue\"");
        Device device = DeviceFixtures.device();;
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        DeviceResponse deviceResponse = deviceService.updatePartial(1, patch);
        assertThat(deviceResponse.getBrand(), CoreMatchers.equalTo("newValue"));
        assertThat(deviceResponse.getName(), CoreMatchers.equalTo(device.getName()));
    }

    @Test
    @DisplayName("When execute the partial update with a id that doesn't exist should throws NotFound exception")
    public void userNotFoundPartialUpdate() {
        Assertions.assertThrows(Exception.class, () -> {
            when(deviceRepository.findById(any())).thenReturn(Optional.empty());
            deviceService.updatePartial(1, any());
        });
    }
}
