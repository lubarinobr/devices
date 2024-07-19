package com.devices;

import com.devices.controller.DeviceController;
import com.devices.service.IDeviceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeviceControllerTest {

    @Mock
    private IDeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @Test
    @DisplayName("Given a empty search criteria should return all")
    public void testSearchAll() {
        deviceController.findDeviceByParam(new HashMap<>());
        verify(deviceService, times(1)).getAllDevices(any());
        verify(deviceService, times(0)).findById(any(), any());
        verify(deviceService, times(0)).searchByBrand(any(), any());
    }

    @Test
    @DisplayName("Given an id as search criteria should return by ID")
    public void testSearchById() {
        deviceController.findDeviceByParam(Map.of("id", "1"));
        verify(deviceService, times(1)).findById(any(), any());
        verify(deviceService, times(0)).getAllDevices(any());
        verify(deviceService, times(0)).searchByBrand(any(), any());
    }

    @Test
    @DisplayName("Given a brand as search criteria should return by Brand")
    public void testSearchByBrand() {
        deviceController.findDeviceByParam(Map.of("brand", "test"));
        verify(deviceService, times(1)).searchByBrand(any(), any());
        verify(deviceService, times(0)).findById(any(), any());
        verify(deviceService, times(0)).getAllDevices(any());
    }

    @Test
    @DisplayName("Given a unmapped attribute as search criteria should be ignored and return all")
    public void testSearchUnmapped() {
        deviceController.findDeviceByParam(Map.of("xpto", "test"));
        verify(deviceService, times(1)).getAllDevices(any());
        verify(deviceService, times(0)).searchByBrand(any(), any());
        verify(deviceService, times(0)).findById(any(), any());
    }
}
