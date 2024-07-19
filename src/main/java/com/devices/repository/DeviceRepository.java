package com.devices.repository;

import com.devices.dto.DeviceResponse;
import com.devices.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    public Page<Device> findByBrand(String brand, Pageable pageable);
    public Page<Device> findById(Integer id, Pageable pageable);
}
