package com.devices;

import com.devices.Fixtures.DeviceFixtures;
import com.devices.controller.DeviceController;
import com.devices.dto.DeviceCreationRequest;
import com.devices.dto.DeviceResponse;
import com.devices.service.IDeviceService;
import jakarta.servlet.http.HttpServletRequest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceControllerTest {

    @InjectMocks
    private DeviceController deviceController;

    @Mock
    private IDeviceService deviceService;
    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    @DisplayName("Given a empty search criteria should return all")
    public void testSearchAll() {
        deviceController.findDeviceByParam(new HashMap<>());
        verify(deviceService, times(1)).findAll(any());
        verify(deviceService, times(0)).findById(any(), any());
        verify(deviceService, times(0)).findByBrand(any(), any());
    }

    @Test
    @DisplayName("Given an id as search criteria should return by ID")
    public void testSearchById() {
        deviceController.findDeviceByParam(Map.of("id", "1"));
        verify(deviceService, times(1)).findById(any(), any());
        verify(deviceService, times(0)).findAll(any());
        verify(deviceService, times(0)).findByBrand(any(), any());
    }

    @Test
    @DisplayName("Given a brand as search criteria should return by Brand")
    public void testSearchByBrand() {
        deviceController.findDeviceByParam(Map.of("brand", "test"));
        verify(deviceService, times(1)).findByBrand(any(), any());
        verify(deviceService, times(0)).findById(any(), any());
        verify(deviceService, times(0)).findAll(any());
    }

    @Test
    @DisplayName("Given a unmapped attribute as search criteria should be ignored and return all")
    public void testSearchUnmapped() {
        deviceController.findDeviceByParam(Map.of("xpto", "test"));
        verify(deviceService, times(1)).findAll(any());
        verify(deviceService, times(0)).findByBrand(any(), any());
        verify(deviceService, times(0)).findById(any(), any());
    }

    @Test
    @DisplayName("Given a valid request body should call the service to save it")
    public void testAddNewDevice() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequest));
        DeviceCreationRequest request = DeviceFixtures.newDeviceCreationRequest();
        when(deviceService.addDevice(request))
                .thenReturn(DeviceFixtures.deviceResponse());

        ResponseEntity<DeviceResponse> response = deviceController.save(request);
        assertThat(response.getStatusCode().value(), CoreMatchers.equalTo(HttpStatus.CREATED.value()));
        assertThat(response.getHeaders(), hasKey("Location"));
        assertThat(response.getHeaders().getLocation().toString(), containsString("?id=1"));
        verify(deviceService, times(1)).addDevice(request);
    }

    @Test
    @DisplayName("Given a valid id should call the service to delete it")
    public void testDeleteDevice() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequest));
        ResponseEntity<Void> response = deviceController.deleteById(1);
        assertThat(response.getStatusCode().value(), CoreMatchers.equalTo(HttpStatus.OK.value()));
        verify(deviceService, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Given a valid request should call the service to update the entity")
    public void testUpdateDevice() throws Exception {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequest));
        DeviceCreationRequest request = new DeviceCreationRequest();
        when(deviceService.update(1, request))
                .thenReturn(DeviceFixtures.deviceResponse());

        ResponseEntity<DeviceResponse> response = deviceController.updateEntity(1, request);
        assertThat(response.getStatusCode().value(), CoreMatchers.equalTo(HttpStatus.OK.value()));
        verify(deviceService, times(1)).update(1, request);
    }
}
