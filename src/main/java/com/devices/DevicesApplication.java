package com.devices;

import com.devices.entities.Device;
import com.devices.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class DevicesApplication implements CommandLineRunner {

	@Autowired
	private DeviceRepository deviceRepository;

	public static void main(String[] args) {
		SpringApplication.run(DevicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		deviceRepository.save(new Device(1, "test", "test", LocalDateTime.now()));
	}
}
