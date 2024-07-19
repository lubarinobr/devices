package com.devices.repository;

import com.devices.dto.DeviceResponse;
import com.devices.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeviceRepository extends PagingAndSortingRepository<Device, Integer> {

    public Page<DeviceResponse> findByBrand(String brand, Pageable pageable);
}
